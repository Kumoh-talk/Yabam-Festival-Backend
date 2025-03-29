package domain.pos.store.entity;

import domain.pos.member.entity.Owner;
import lombok.Getter;

@Getter
public class Store {
	private final Long storeId;
	private final StoreInfo storeInfo;
	private final Owner storeOwner;

	public Store(Long storeId, StoreInfo storeInfo, Owner storeOwner) {
		this.storeId = storeId;
		this.storeInfo = storeInfo;
		this.storeOwner = storeOwner;
	}
}
