package domain.pos.menu.repository;

import java.util.Optional;

import domain.pos.menu.entity.MenuCategory;

public interface MenuCategoryRepository {
	MenuCategory postMenuCategory(String categoryName);

	Optional<MenuCategory> getMenuCategory(Long categoryId);
}
