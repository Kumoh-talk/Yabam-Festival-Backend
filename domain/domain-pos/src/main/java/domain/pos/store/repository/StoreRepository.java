package domain.pos.store.repository;

import domain.pos.member.entity.Owner;
import domain.pos.store.entity.Store;
import domain.pos.store.entity.StoreInfo;

public interface StoreRepository {
	Long createStore(Owner owner, StoreInfo createRequestStoreInfo);

	Store findStoreByStoreId(Long storeId);

	Store changeStoreInfo(Store previousStore, StoreInfo requestChangeStoreInfo);
}
