package domain.pos.store.entity;

import domain.pos.member.entity.Owner;
import lombok.Getter;

@Getter
public class Store {
	private final Long storeId;
	private final Boolean isOpen;
	private final StoreInfo storeInfo;
	private final Owner storeOwner;

	public Store(Long storeId, Boolean isOpen, StoreInfo storeInfo, Owner storeOwner) {
		this.storeId = storeId;
		this.isOpen = isOpen;
		this.storeInfo = storeInfo;
		this.storeOwner = storeOwner;
	}
}
