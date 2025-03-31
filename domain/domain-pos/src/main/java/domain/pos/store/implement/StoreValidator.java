package domain.pos.store.implement;

import org.springframework.stereotype.Component;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import domain.pos.store.entity.Store;
import lombok.RequiredArgsConstructor;

@Component
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
}
