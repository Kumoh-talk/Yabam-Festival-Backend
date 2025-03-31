package fixtures.menu;

import domain.pos.menu.entity.Menu;
import domain.pos.menu.entity.MenuCategory;
import domain.pos.menu.entity.MenuInfo;
import domain.pos.store.entity.StoreInfo;

public class MenuFixture {
	public static Menu CUSTOM_MENU(MenuInfo menuInfo, StoreInfo storeInfo, MenuCategory menuCategory) {
		return Menu.builder()
			.menuInfo(menuInfo)
			.storeInfo(storeInfo)
			.menuCategory(menuCategory)
			.build();
	}
}
