package org.food.service;

import lombok.RequiredArgsConstructor;
import org.food.api.repository.MealRepository;
import org.food.api.service.MealService;
import org.food.dto.MealDto;
import org.food.model.Meal;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

        private final ModelMapper modelMapper;

        private final MealRepository mealRepository;

        @Override
        public List<MealDto> getAllMeals(int id, int limit){

                Type listType = new TypeToken<List<MealDto>>(){}.getType();
                return modelMapper.map(mealRepository.findAll(id, limit),listType);
        }

        @Override
        public MealDto addMeal(MealDto mealDto){

                Meal meal = modelMapper.map(mealDto, Meal.class);
                return modelMapper.map(mealRepository.create(meal), MealDto.class);
        }

        @Override
        public MealDto getMeal(Integer id){

                return modelMapper.map(mealRepository.findById(id), MealDto.class);
        }

        @Override
        public void deleteMealById(Integer id) {

                Meal meal = mealRepository.findById(id);
                mealRepository.delete(meal);
        }


        @Override
        public void update(MealDto mealDto) {

                mealRepository.update(modelMapper.map(mealDto, Meal.class));
        }
}
