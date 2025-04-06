package domain.pos.menu.repository;

import java.util.List;
import java.util.Optional;

import domain.pos.menu.entity.MenuCategory;

public interface MenuCategoryRepository {
	MenuCategory postMenuCategory(Long storeId, String categoryName);

	Optional<MenuCategory> getMenuCategory(Long categoryId);

	List<MenuCategory> getMenuCategoryList(Long storeId);

	MenuCategory patchMenuCategory(Long categoryId, String categoryName);

	void deleteMenuCategory(Long categoryId);

}
