package domain.pos.store.service;

import org.springframework.stereotype.Service;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import domain.pos.member.entity.UserPassport;
import domain.pos.member.entity.UserRole;
import domain.pos.member.implement.OwnerReader;
import domain.pos.store.entity.Store;
import domain.pos.store.entity.StoreInfo;
import domain.pos.store.implement.StoreReader;
import domain.pos.store.implement.StoreWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {
	private final OwnerReader ownerReader;
	private final StoreWriter storeWriter;
	private final StoreReader storeReader;

	public Long createStore(final UserPassport userPassport, final StoreInfo createRequestStoreInfo) {
		if (isOwner(userPassport)) {
			log.warn("점주가 아닌 사용자의 요청으로 인한 실패: userId={}", userPassport.getUserId());
			throw new ServiceException(ErrorCode.NOT_VALID_OWNER);
		}

		final Long savedStoreId = storeWriter.createStore(userPassport, createRequestStoreInfo);
		log.info("가게 생성 성공 : userId={}, storeId={}", userPassport.getUserId(), savedStoreId);
		return savedStoreId;
	}

	// 점주가 아닌 사용자인지 확인
	private boolean isOwner(UserPassport userPassport) {
		return !userPassport.getUserRole().equals(UserRole.ROLE_OWNER);
	}

	public Store findStore(final Long storeId) {
		log.info("가게 조회: storeId={}", storeId);
		return storeReader.readSingleStore(storeId)
			.orElseThrow(() -> {
				log.warn("가게 조회 실패: storeId={}", storeId);
				throw new ServiceException(ErrorCode.NOT_FOUND_STORE);
			});
	}

	public Store updateStoreInfo(
		final UserPassport userPassport,
		final Long queryStoreId,
		final StoreInfo requestChangeStoreInfo) {
		if (isOwner(userPassport)) {
			log.warn("점주가 아닌 사용자의 요청으로 인한 실패: userId={}", userPassport.getUserId());
			throw new ServiceException(ErrorCode.NOT_VALID_OWNER);
		}
		final Store previousStore = storeReader.readSingleStore(queryStoreId)
			.orElseThrow(() -> {
				log.warn("가게 조회 실패: storeId={}", queryStoreId);
				throw new ServiceException(ErrorCode.NOT_FOUND_STORE);
			});

		if (isEqualSavedStoreOwnerAndQueryOwner(userPassport.getUserId(), previousStore)) {
			log.warn("수정 요청 실패: userId={}, queryStoreId={}", userPassport.getUserId(), queryStoreId);
			throw new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER);
		}

		Store updatedStore = storeWriter
			.updateStoreInfo(previousStore, requestChangeStoreInfo);
		log.info("가게 정보 수정 성공 : userId={}, storeId={}", userPassport.getUserId(), queryStoreId);

		return updatedStore;
	}

	// 가게 소유자와 요청 점주가 같은지 확인
	private boolean isEqualSavedStoreOwnerAndQueryOwner(Long ownerId, Store previousStore) {
		return !previousStore.getStoreOwner().getOwnerId().equals(ownerId);
	}

	public void deleteStore(UserPassport userPassport, Long storeId) {
		if (isOwner(userPassport)) {
			log.warn("점주가 아닌 사용자의 요청으로 인한 실패: userId={}", userPassport.getUserId());
			throw new ServiceException(ErrorCode.NOT_VALID_OWNER);
		}
		final Store previousStore = storeReader.readSingleStore(storeId)
			.orElseThrow(() -> {
				log.warn("가게 조회 실패: storeId={}", storeId);
				throw new ServiceException(ErrorCode.NOT_FOUND_STORE);
			});
		if (isEqualSavedStoreOwnerAndQueryOwner(userPassport.getUserId(), previousStore)) {
			log.warn("삭제 요청 실패: userId={}, queryStoreId={}", userPassport.getUserId(), storeId);
			throw new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER);
		}
		storeWriter.deleteStore(previousStore);
		log.info("가게 삭제 성공 : userId={}, storeId={}", userPassport.getUserId(), storeId);
	}
}
