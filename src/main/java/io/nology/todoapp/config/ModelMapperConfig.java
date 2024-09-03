package io.nology.todoapp.config;

import io.nology.todoapp.category.Category;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.nology.todoapp.category.CreateCategoryDTO;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.typeMap(String.class, String.class).setConverter(new StringTrimConverter());
        mapper.typeMap(CreateCategoryDTO.class, Category.class).addMappings(m -> m
                .using(new CreateCategoryDTOToCategoryConvertor()).map(CreateCategoryDTO::getName, Category::setName));
        return mapper;
    }

    private class StringTrimConverter implements Converter<String, String> {

        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().trim();

        }

    }

    private class CreateCategoryDTOToCategoryConvertor implements Converter<String, String> {

        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().trim().toLowerCase();
        }

    }
}
