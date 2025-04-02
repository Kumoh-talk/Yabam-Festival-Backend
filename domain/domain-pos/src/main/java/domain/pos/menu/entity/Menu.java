package domain.pos.menu.entity;

import domain.pos.store.entity.StoreInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Menu {
	private MenuInfo menuInfo;
	private StoreInfo storeInfo;
	private MenuCategory menuCategory;

	@Builder
	public Menu(MenuInfo menuInfo, StoreInfo storeInfo, MenuCategory menuCategory) {
		this.menuInfo = menuInfo;
		this.storeInfo = storeInfo;
		this.menuCategory = menuCategory;
	}
}
