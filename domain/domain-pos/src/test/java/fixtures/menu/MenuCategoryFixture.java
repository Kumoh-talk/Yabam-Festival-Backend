package fixtures.menu;

import domain.pos.menu.entity.MenuCategory;

public class MenuCategoryFixture {
	public static final Long GENERAL_MENU_CATEGORY_ID = 1L;
	public static final String GENERAL_CATEGORY_NAME = "categoryName";
	public static final Long GENERAL_STORE_ID = 1L;

	public static MenuCategory GENERAL_MENU_CATEGORY() {
		return MenuCategory.builder()
			.menuCategoryId(GENERAL_MENU_CATEGORY_ID)
			.menuCategoryName(GENERAL_CATEGORY_NAME)
			.storeId(GENERAL_STORE_ID)
			.build();
	}

	public static MenuCategory CUSTOM_MENU_CATEGORY(Long menuCategoryId, Long storeId) {
		return MenuCategory.builder()
			.menuCategoryId(menuCategoryId)
			.menuCategoryName(GENERAL_CATEGORY_NAME)
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
