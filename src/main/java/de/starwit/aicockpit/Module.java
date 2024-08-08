package de.starwit.aicockpit;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.starwit.aicockpit.ModelType;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Module
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-08T12:46:51.626255744+02:00[Europe/Berlin]")
public class Module {

  private Long id;

  private String name;

  private String description;

  private Boolean useAI;

  private ModelType aiType;

  private String modelVersion;

  public Module id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Module name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Module description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Module useAI(Boolean useAI) {
    this.useAI = useAI;
    return this;
  }

  /**
   * Get useAI
   * @return useAI
  */
  
  @Schema(name = "useAI", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("useAI")
  public Boolean getUseAI() {
    return useAI;
  }

  public void setUseAI(Boolean useAI) {
    this.useAI = useAI;
  }

  public Module aiType(ModelType aiType) {
    this.aiType = aiType;
    return this;
  }

  /**
   * Get aiType
   * @return aiType
  */
  @Valid 
  @Schema(name = "aiType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("aiType")
  public ModelType getAiType() {
    return aiType;
  }

  public void setAiType(ModelType aiType) {
    this.aiType = aiType;
  }

  public Module modelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
    return this;
  }

  /**
   * Get modelVersion
   * @return modelVersion
  */
  
  @Schema(name = "modelVersion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("modelVersion")
  public String getModelVersion() {
    return modelVersion;
  }

  public void setModelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Module module = (Module) o;
    return Objects.equals(this.id, module.id) &&
        Objects.equals(this.name, module.name) &&
        Objects.equals(this.description, module.description) &&
        Objects.equals(this.useAI, module.useAI) &&
        Objects.equals(this.aiType, module.aiType) &&
        Objects.equals(this.modelVersion, module.modelVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, useAI, aiType, modelVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Module {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    useAI: ").append(toIndentedString(useAI)).append("\n");
    sb.append("    aiType: ").append(toIndentedString(aiType)).append("\n");
    sb.append("    modelVersion: ").append(toIndentedString(modelVersion)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

