/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.11.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package de.starwit;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import de.starwit.aic.model.Module;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-02-03T18:31:51.886875451+01:00[Europe/Berlin]", comments = "Generator version: 7.11.0")
@Validated
@Tag(name = "modules", description = "the modules API")
public interface ModulesApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
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
                    String exampleString = "[ { \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"submodules\" : [ null, null ], \"name\" : \"name\", \"successors\" : [ null, null ], \"description\" : \"description\", \"useAI\" : true, \"model\" : { \"publicTrainingData\" : true, \"modelLink\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"linkToPublicTrainingData\" : \"https://openapi-generator.tech\", \"lastDeployment\" : \"2000-01-23T04:56:07.000+00:00\", \"modelType\" : \"LLM\", \"version\" : \"version\" }, \"id\" : 0, \"actionTypes\" : [ { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] }, { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] } ], \"sBOMLocation\" : { \"key\" : { \"url\" : \"url\" } } }, { \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"submodules\" : [ null, null ], \"name\" : \"name\", \"successors\" : [ null, null ], \"description\" : \"description\", \"useAI\" : true, \"model\" : { \"publicTrainingData\" : true, \"modelLink\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"linkToPublicTrainingData\" : \"https://openapi-generator.tech\", \"lastDeployment\" : \"2000-01-23T04:56:07.000+00:00\", \"modelType\" : \"LLM\", \"version\" : \"version\" }, \"id\" : 0, \"actionTypes\" : [ { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] }, { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] } ], \"sBOMLocation\" : { \"key\" : { \"url\" : \"url\" } } } ]";
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
                    String exampleString = "[ { \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"submodules\" : [ null, null ], \"name\" : \"name\", \"successors\" : [ null, null ], \"description\" : \"description\", \"useAI\" : true, \"model\" : { \"publicTrainingData\" : true, \"modelLink\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"linkToPublicTrainingData\" : \"https://openapi-generator.tech\", \"lastDeployment\" : \"2000-01-23T04:56:07.000+00:00\", \"modelType\" : \"LLM\", \"version\" : \"version\" }, \"id\" : 0, \"actionTypes\" : [ { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] }, { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] } ], \"sBOMLocation\" : { \"key\" : { \"url\" : \"url\" } } }, { \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"submodules\" : [ null, null ], \"name\" : \"name\", \"successors\" : [ null, null ], \"description\" : \"description\", \"useAI\" : true, \"model\" : { \"publicTrainingData\" : true, \"modelLink\" : \"https://openapi-generator.tech\", \"name\" : \"name\", \"linkToPublicTrainingData\" : \"https://openapi-generator.tech\", \"lastDeployment\" : \"2000-01-23T04:56:07.000+00:00\", \"modelType\" : \"LLM\", \"version\" : \"version\" }, \"id\" : 0, \"actionTypes\" : [ { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] }, { \"endpoint\" : \"endpoint\", \"decisionTypes\" : [ { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] } ], \"executionPolicy\" : \"MANUAL\", \"name\" : \"name\", \"description\" : \"description\", \"id\" : 6, \"actions\" : [ { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 }, { \"metadata\" : \"metadata\", \"creationTime\" : \"2000-01-23T04:56:07.000+00:00\", \"decision\" : { \"cameraLatitude\" : 5.637376656633329, \"actionVisualizationUrl\" : \"actionVisualizationUrl\", \"mediaUrl\" : \"mediaUrl\", \"description\" : \"description\", \"decisionType\" : { \"name\" : \"name\", \"description\" : \"description\", \"decisions\" : [ null, null ], \"id\" : 7, \"actionTypes\" : [ null, null ] }, \"id\" : 5, \"acquisitionTime\" : \"2000-01-23T04:56:07.000+00:00\", \"cameraLongitude\" : 2.3021358869347655, \"state\" : \"NEW\", \"actions\" : [ null, null ] }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : 1 } ] } ], \"sBOMLocation\" : { \"key\" : { \"url\" : \"url\" } } } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /modules/register
     * register a new module
     *
     * @param module Module test to be registered (required)
     * @return created a new module (status code 200)
     */
    @Operation(
        operationId = "registerModule",
        description = "register a new module",
        responses = {
            @ApiResponse(responseCode = "200", description = "created a new module")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/modules/register",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> registerModule(
        @Parameter(name = "Module", description = "Module test to be registered", required = true) @Valid @RequestBody Module module
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /modules/register/{id}
     * update a module
     *
     * @param id Numeric ID of module test to be updated (required)
     * @param module Module test to be updated (required)
     * @return updated a module (status code 200)
     */
    @Operation(
        operationId = "updateModule",
        description = "update a module",
        responses = {
            @ApiResponse(responseCode = "200", description = "updated a module")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/modules/register/{id}",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> updateModule(
        @Parameter(name = "id", description = "Numeric ID of module test to be updated", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
        @Parameter(name = "Module", description = "Module test to be updated", required = true) @Valid @RequestBody Module module
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
