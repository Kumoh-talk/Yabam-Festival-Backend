package fixtures.menu;

import domain.pos.menu.entity.MenuCategory;

public class MenuCategoryFixture {
	public static final Long MENU_CATEGORY_ID = 1L;
	public static final String CATEGORY_NAME = "categoryName";
	public static final Long STORE_ID = 1L;

	public static MenuCategory GENERAL_MENU_CATEGORY() {
		return MenuCategory.builder()
			.menuCategoryId(MENU_CATEGORY_ID)
			.menuCategoryName(CATEGORY_NAME)
			.storeId(STORE_ID)
			.build();
	}

	public static MenuCategory CUSTOM_MENU_CATEGORY(Long menuCategoryId, Long storeId) {
		return MenuCategory.builder()
			.menuCategoryId(menuCategoryId)
			.menuCategoryName(CATEGORY_NAME)
			.storeId(storeId)
			.build();
	}

	public static MenuCategory CUSTOM_MENU_CATEGORY(Long menuCategoryId, String categoryName, Long storeId) {
		return MenuCategory.builder()
			.menuCategoryId(menuCategoryId)
			.menuCategoryName(categoryName)
			.storeId(storeId)
			.build();
	}
}
