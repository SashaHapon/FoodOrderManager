package org.food.service;

import jakarta.persistence.EntityNotFoundException;
import org.food.api.repository.MealRepository;
import org.food.dto.MealDto;
import org.food.model.Meal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MealServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private MealRepository mealRepository;
    @InjectMocks
    private MealServiceImpl mealService;

    @Test
    @DisplayName("Must be returned all meals")
    public void should_returnAllMeals_when_tryToGetAllMeals() {

        int id = 1;
        int limit = 10;

        List<Meal> mockMeals = Arrays.asList(new Meal(), new Meal(), new Meal());
        List<MealDto> mockMealDtos = Arrays.asList(new MealDto(), new MealDto(), new MealDto());

        when(mealRepository.findAll(id, limit)).thenReturn(mockMeals);
        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();

        when(modelMapper.map(mockMeals, listType)).thenReturn(mockMealDtos);

        List<MealDto> mealDtos = mealService.getAllMeals(id, limit);

        assertThat(mealDtos).isEqualTo(mockMealDtos);
    }

    @Test
    @DisplayName("Throw exception when try to get all meals")
    public void should_throwException_when_tryToGetAllMeals() {
        int id = 1;
        int limit = 10;

        List<Meal> mockMeals = Arrays.asList(new Meal(), new Meal(), new Meal());

        when(mealRepository.findAll(id, limit)).thenReturn(mockMeals);
        Type listType = new TypeToken<List<MealDto>>() {
        }.getType();
        when(modelMapper.map(mealRepository.findAll(id, limit), listType)).thenThrow(IllegalArgumentException.class);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> mealService.getAllMeals(id, limit));
    }

    @Test
    @DisplayName("Return meal after adding meal")
    public void should_returnMeal_when_tryToAddMeal() {
        MealDto mealDto = new MealDto();
        Meal mappedMeal = new Meal();
        Meal returnMeal = new Meal();
        MealDto expectedMealDtoOutput = new MealDto();

        when(modelMapper.map(mealDto, Meal.class)).thenReturn(mappedMeal);
        when(mealRepository.create(mappedMeal)).thenReturn(returnMeal);
        when(modelMapper.map(returnMeal, MealDto.class)).thenReturn(expectedMealDtoOutput);

        MealDto createdMealDto = mealService.addMeal(mealDto);

        assertThat(expectedMealDtoOutput).isEqualTo(createdMealDto);
    }

    @Test
    @DisplayName("Throw exception when add meal")
    public void should_throwException_when_tryToAddAccount() {
        MealDto mealDto = new org.food.dto.MealDto();
        Meal mappedMeal = new Meal();

        when(modelMapper.map(mealDto, Meal.class)).thenReturn(mappedMeal);
        when(mealRepository.create(mappedMeal)).thenThrow(IllegalArgumentException.class);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> mealService.addMeal(mealDto));
    }

    @Test
    @DisplayName("Return meal searching by id")
    public void should_returnAccount_whenTryToGetAccount() {
        int id = 1;
        MealDto expectedMealDtoOutput = new MealDto();
        Meal testMeal = new Meal();

        when(mealRepository.findById(id)).thenReturn(testMeal);
        when(modelMapper.map(testMeal, MealDto.class)).thenReturn(expectedMealDtoOutput);

        MealDto createdMealDto = mealService.getMeal(id);

        assertThat(expectedMealDtoOutput).isEqualTo(createdMealDto);
    }

    @Test
    @DisplayName("Throw exception when get meal by id")
    public void should_throwException_when_tryToGetMeal() {
        int id = 1;

        when(mealRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> mealService.getMeal(id));
    }

    @Test
    @DisplayName("Check that method calls one time")
    public void should_check_methodsCalls_when_tryToDeleteMealById() {

        Meal testMeal = new Meal();
        testMeal.setId(1);

        when(mealRepository.findById(1)).thenReturn(testMeal);

        mealService.deleteMealById(1);

        verify(mealRepository, times(1)).delete(testMeal);
    }

    @Test
    @DisplayName("Throw exception when try to delete meal")
    public void should_throwException_when_tryToDeleteMeal() {
        Integer id = 1;

        when(mealRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> mealService.deleteMealById(id));
    }

    @Test
    @DisplayName("returnMeal_when_updateMeal")
    public void should_returnMeal_when_tryToUpdateMeal() {

        MealDto mealDto = new MealDto();
        mealDto.setId(1);

        Meal testMeal = new Meal();
        testMeal.setId(1);
        testMeal.setName("Dave");

        when(modelMapper.map(mealDto, Meal.class)).thenReturn(testMeal);
        when(mealRepository.update(testMeal)).thenReturn(testMeal);

        mealService.update(mealDto);

        verify(mealRepository, times(1)).update(testMeal);
    }

    @Test
    @DisplayName("throwException_when_updateMeal")
    public void should_throwException_when_tryToUpdateMeal() {

        MealDto mealDto = new MealDto();
        Meal testMeal = new Meal();

        when(modelMapper.map(mealDto,Meal.class)).thenReturn(testMeal);
        when(mealRepository.update(testMeal)).thenThrow(NullPointerException.class);

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> mealService.update(mealDto));
    }
}
