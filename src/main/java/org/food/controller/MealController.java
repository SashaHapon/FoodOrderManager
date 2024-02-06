package org.food.controller;

import lombok.RequiredArgsConstructor;
import org.food.api.service.MealService;
import org.food.dto.MealDto;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
public class MealController {

    private final MealService mealService;

    @GetMapping("/")
    public List<MealDto> getAll(@RequestParam(defaultValue = "1", required = false) int id,
                                @RequestParam(defaultValue = "10", required = false) int limit) {

        return mealService.getAllMeals(id, limit);
    }

    @PostMapping("/")
    public MealDto addMeal(@RequestBody MealDto mealDto) {

        return mealService.addMeal(mealDto);
    }

    @GetMapping("/{id}")
    public MealDto getMeal(@PathVariable("id") Integer id){

        return mealService.getMeal(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMealById(@PathVariable("id") Integer id){

        mealService.deleteMealById(id);
    }

    @PutMapping("/{id}")
    void update(@RequestBody MealDto mealDto){

        mealService.update(mealDto);
    }
}
