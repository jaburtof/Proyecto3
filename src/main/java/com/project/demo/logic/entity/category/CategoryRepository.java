package com.project.demo.logic.entity.category;

import com.project.demo.logic.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
