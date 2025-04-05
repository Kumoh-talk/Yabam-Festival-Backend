package domain.pos.store.repository;

import java.util.Optional;

import domain.pos.member.entity.UserPassport;
import domain.pos.store.entity.Store;
import domain.pos.store.entity.StoreInfo;

public interface StoreRepository {
	Long createStore(UserPassport userPassport, StoreInfo createRequestStoreInfo);

	Optional<Store> findStoreByStoreId(Long storeId);

	Store changeStoreInfo(Store previousStore, StoreInfo requestChangeStoreInfo);

	void deleteStore(Store previousStore);

	Store changeStoreOpenStatus(Store previousStore);
}
