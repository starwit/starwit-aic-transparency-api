/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.2.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package de.starwit;

import de.starwit.aicockpit.Module;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-08T13:29:19.151566481+02:00[Europe/Berlin]")
@Validated
@Tag(name = "modules", description = "the modules API")
public interface ModulesApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /modules
     * create a new module
     *
     * @param module Module test to be created (required)
     * @return create new bias test (status code 200)
     */
    @Operation(
        operationId = "createModule",
        description = "create a new module",
        responses = {
            @ApiResponse(responseCode = "200", description = "create new bias test")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/modules",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> createModule(
        @Parameter(name = "Module", description = "Module test to be created", required = true) @Valid @RequestBody Module module
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /modules/{id}
     * delivers components/modules with provided id
     *
     * @param id Numeric ID of module (required)
     * @return get single module (status code 200)
     */
    @Operation(
        operationId = "getModule",
        description = "delivers components/modules with provided id",
        responses = {
            @ApiResponse(responseCode = "200", description = "get single module", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Module.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/modules/{id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<Module>> getModule(
        @Parameter(name = "id", description = "Numeric ID of module", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"modelVersion\" : \"modelVersion\", \"name\" : \"name\", \"description\" : \"description\", \"useAI\" : true, \"id\" : 0 }, { \"modelVersion\" : \"modelVersion\", \"name\" : \"name\", \"description\" : \"description\", \"useAI\" : true, \"id\" : 0 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /modules
     * delivers a list of components/modules of which AI system is composed
     *
     * @return module list (status code 200)
     */
    @Operation(
        operationId = "getModules",
        description = "delivers a list of components/modules of which AI system is composed",
        responses = {
            @ApiResponse(responseCode = "200", description = "module list", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Module.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/modules",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<Module>> getModules(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"modelVersion\" : \"modelVersion\", \"name\" : \"name\", \"description\" : \"description\", \"useAI\" : true, \"id\" : 0 }, { \"modelVersion\" : \"modelVersion\", \"name\" : \"name\", \"description\" : \"description\", \"useAI\" : true, \"id\" : 0 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /modules/{id}
     * update a module
     *
     * @param id Numeric ID of module test to be updated (required)
     * @param module Module test to be updated (required)
     * @return update a module test (status code 200)
     */
    @Operation(
        operationId = "updateModule",
        description = "update a module",
        responses = {
            @ApiResponse(responseCode = "200", description = "update a module test")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/modules/{id}",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> updateModule(
        @Parameter(name = "id", description = "Numeric ID of module test to be updated", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
        @Parameter(name = "Module", description = "Module test to be updated", required = true) @Valid @RequestBody Module module
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}