package org.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.food.api.service.MealService;
import org.food.dto.MealDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(name="MealController", description=" Позволяет контролировать блюда в бд")
public class MealController {

    private final MealService mealService;

    @Operation(
            summary = "Получение всех блюд",
            description = "Позволяет получить все блюда из базы данных"
    )
    @GetMapping("/")
    public List<MealDto> getAll(@RequestParam(defaultValue = "1", required = false)
                                    @Parameter(description = "Строка отсчета") int id,
                                @RequestParam(defaultValue = "10", required = false)
                                    @Parameter(description = "Количество строк") int limit) {

        return mealService.getAllMeals(id, limit);
    }

    @Operation(
            summary = "Добавление блюда",
            description = "Позволяет добавить блюдо в базу данных"
    )
    @PostMapping("/")
    public MealDto addMeal(@RequestBody
                               @Parameter(description = "Данные нового блюда") MealDto mealDto) {

        return mealService.addMeal(mealDto);
    }

    @Operation(
            summary = "Получение блюда",
            description = "Позволяет получить блюдо по id из базы данных"
    )
    @GetMapping("/{id}")
    public MealDto getMeal(@PathVariable("id")
                               @Parameter(description = "Идентификатор блюда") Integer id){

        return mealService.getMeal(id);
    }

    @Operation(
            summary = "Удаление блюда",
            description = "Позволяет удалить блюдо по id из базы данных"
    )
    @DeleteMapping("/{id}")
    public void deleteMealById(@PathVariable("id")
                                   @Parameter(description = "Идентификатор блюда") Integer id){

        mealService.deleteMealById(id);
    }

    @Operation(
            summary = "Обновление блюда",
            description = "Позволяет обновить данные о блюде в базе данных"
    )
    @PutMapping("/{id}")
    void update(@RequestBody
                @Parameter(description = "Данные обновления блюда") MealDto mealDto){

        mealService.update(mealDto);
    }
}
