package domain.pos.store.repository;

import domain.pos.member.entity.Owner;
import domain.pos.store.entity.Store;

public interface StoreRepository {
	Long createStore(Owner owner, Store createRequestStore);
}
