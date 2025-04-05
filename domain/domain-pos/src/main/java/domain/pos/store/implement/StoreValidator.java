package domain.pos.store.implement;

import org.springframework.stereotype.Component;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import domain.pos.member.entity.UserPassport;
import domain.pos.member.entity.UserRole;
import domain.pos.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class StoreValidator {
	private final StoreReader storeReader;

	public void validateStoreOwner(Long storeId, Long userId) {
		Store store = storeReader.readSingleStore(storeId)
			.orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_STORE));
		if (!store.getStoreOwner().getOwnerId().equals(userId)) {
			throw new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER);
		}
	}

	public Store validateStoreByUser(UserPassport userPassport, Long queryStoreId) {
		if (isOwner(userPassport)) {
			log.warn("점주가 아닌 사용자의 요청으로 인한 실패: userId={}", userPassport.getUserId());
			throw new ServiceException(ErrorCode.NOT_VALID_OWNER);
		}
		final Store previousStore = storeReader.readSingleStore(queryStoreId)
			.orElseThrow(() -> {
				log.warn("해당 Store 존재하지 않음: storeId={}", queryStoreId);
				throw new ServiceException(ErrorCode.NOT_FOUND_STORE);
			});

		if (isEqualSavedStoreOwnerAndQueryOwner(userPassport.getUserId(), previousStore)) {
			log.warn("요청 유저는 Store 소유자와 다름: userId={}, queryStoreId={}", userPassport.getUserId(), queryStoreId);
			throw new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER);
		}
		return previousStore;
	}

	// 점주가 아닌 사용자인지 확인
	private boolean isOwner(UserPassport userPassport) {
		return !userPassport.getUserRole().equals(UserRole.ROLE_OWNER);
	}

	// 가게 소유자와 요청 점주가 같은지 확인
	private boolean isEqualSavedStoreOwnerAndQueryOwner(Long ownerId, Store previousStore) {
		return !previousStore.getStoreOwner().getOwnerId().equals(ownerId);
	}
}
