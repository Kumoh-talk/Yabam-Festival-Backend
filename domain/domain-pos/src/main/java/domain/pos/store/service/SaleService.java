package domain.pos.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import domain.pos.member.entity.UserPassport;
import domain.pos.member.entity.UserRole;
import domain.pos.store.entity.Sale;
import domain.pos.store.entity.Store;
import domain.pos.store.implement.SaleReader;
import domain.pos.store.implement.SaleWriter;
import domain.pos.store.implement.StoreValidator;
import domain.pos.store.implement.StoreWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleService {
	private final SaleWriter saleWriter;
	private final SaleReader saleReader;
	private final StoreWriter storeWriter;
	private final StoreValidator storeValidator;
	private final PlatformTransactionManager transactionManager;

	public Sale openStore(final UserPassport userPassport, final Long storeId) {
		final Store previousStore = storeValidator.validateStoreModifyByUser(userPassport, storeId);

		if (previousStore.getIsOpen()) {
			log.warn("가게 활성화 변경 실패: userId={}, storeId={}", userPassport.getUserId(), storeId);
			throw new ServiceException(ErrorCode.CONFLICT_OPEN_STORE);
		}

		final Sale createdSale = new TransactionTemplate(transactionManager).execute(status -> {
			final Store opendStore = storeWriter.modifyStoreOpenStatus(previousStore);
			return saleWriter.createSale(opendStore);
		});

		log.info("가게 활성화 성공 : userId={}, storeId={}, saleId={}", userPassport.getUserId(), storeId,
			createdSale.getSaleId());
		return createdSale;
	}

	public Sale closeStore(final UserPassport userPassport, final Long saleId) {
		if (isOwner(userPassport)) {
			log.warn("점주가 아닌 사용자의 요청으로 인한 실패: userId={}", userPassport.getUserId());
			throw new ServiceException(ErrorCode.NOT_VALID_OWNER);
		}
		final Sale savedSale = saleReader.readSingleSale(saleId)
			.orElseThrow(() -> {
				log.warn("판매 내역 조회 실패: saleId={}", saleId);
				throw new ServiceException(ErrorCode.NOT_FOUND_STORE);
			});

		validateOpendSaleOrStore(userPassport, saleId, savedSale);

		final Sale closedSale = new TransactionTemplate(transactionManager).execute(status -> {
			final Store closedStore = storeWriter.modifyStoreOpenStatus(savedSale.getStore());
			return saleWriter.closeSale(savedSale, closedStore);
		});
		log.info("가게 종료 성공 : userId={}, storeId={}, saleId={}", userPassport.getUserId(),
			savedSale.getStore().getStoreId(), closedSale.getSaleId());
		return closedSale;
	}

	// 판매 종료 시점에 가게가 종료된 상태인지 확인
	private static void validateOpendSaleOrStore(UserPassport userPassport, Long saleId, Sale savedSale) {
		savedSale.getCloseDateTime()
			.ifPresent((dateTime) -> {
				log.warn("이미 종료된 Sale.: userId={}, saleId={}", userPassport.getUserId(), saleId);
				throw new ServiceException(ErrorCode.CONFLICT_CLOSE_STORE);
			});
		if (!savedSale.getStore().getIsOpen()) {
			log.warn("이미 종료된 가게 상태: userId={}, storeId={}", userPassport.getUserId(),
				savedSale.getStore().getStoreId());
			throw new ServiceException(ErrorCode.CONFLICT_CLOSE_STORE);
		}
	}

	// 점주가 아닌 사용자인지 확인
	private boolean isOwner(UserPassport userPassport) {
		return !userPassport.getUserRole().equals(UserRole.ROLE_OWNER);
	}

}
