package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelDTO {

    private Long id;

    @NotNull(message = "Name can not be null!")
    private String name;

    @NotNull(message = "Name can not be null!")
    private ManufacturerDTO manufacturer;

    private ModelDTO() {
    }

    private ModelDTO(Long id, String name, ManufacturerDTO manufacturer) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
    }
    
    public static ModelDTOBuilder newBuilder() {
        return new ModelDTOBuilder();
    }
    
    @JsonProperty
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ManufacturerDTO getManufacturer() {
        return manufacturer;
    }

    public static class ModelDTOBuilder {

        private Long id;
        private String name;
        private ManufacturerDTO manufacturer;

        public ModelDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ModelDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ModelDTOBuilder setManufacturer(ManufacturerDTO manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public ModelDTO createModelDTO() {
            return new ModelDTO(id, name, manufacturer);
        }

    }
}
