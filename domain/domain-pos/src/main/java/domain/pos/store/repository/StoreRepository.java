package domain.pos.store.repository;

import domain.pos.member.entity.Owner;
import domain.pos.store.entity.StoreInfo;

public interface StoreRepository {
	Long createStore(Owner owner, StoreInfo createRequestStoreInfo);
}
