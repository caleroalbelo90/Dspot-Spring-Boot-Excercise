package com.dspot.profile.main.controllers;


import com.dspot.profile.main.model.ApiError;
import com.dspot.profile.main.model.profile.Profile;
import com.dspot.profile.main.model.profile.ProfileDTO;
import com.dspot.profile.main.model.profile.ProfilePage;
import com.dspot.profile.main.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    @Operation(
            tags = {"Profile"},
            summary = "Get profiles",
            description = "Get paged profiles in the system, if no profiles are found, an empty page will be returned",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number to be loaded",
                            required = true,
                            example = "1",
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "pageSize",
                            description = "Page size to be loaded",
                            required = true,
                            example = "10",
                            schema = @Schema(implementation = Integer.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profiles page loaded",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProfilePage.class)
                            )
                    )
            })
    @ResponseStatus(HttpStatus.OK)
    public ProfilePage getAllProfiles(@RequestParam int page, @RequestParam int pageSize) {
        Page<Profile> profilesPage = profileService.getProfilesPage(Math.max(0, page - 1), pageSize);
        return new ProfilePage(profilesPage.getContent(), profilesPage.isLast(), profilesPage.getNumberOfElements(), profilesPage.isEmpty());
    }

    @GetMapping(path = "{profileId}")
    @Operation(
            tags = {"Profile"},
            summary = "Find profile",
            description = "Given an id, find a profile with that id, if the profile does not exist, a 404 error will be thrown",
            parameters = {
                    @Parameter(
                            name = "profileId",
                            description = "Profile id to be loaded",
                            required = true,
                            example = "1",
                            schema = @Schema(implementation = Long.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProfileDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"type\": \"about:blank\",\n" +
                                                    "    \"title\": \"Not Found\",\n" +
                                                    "    \"status\": 404,\n" +
                                                    "    \"detail\": \"Profile with id 72 does not exist\",\n" +
                                                    "    \"instance\": \"/api/v1/profile/72\"\n" +
                                                    "}"
                                    )
                            )
                    )
            })
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getProfileById(@PathVariable("profileId") Long profileId) {
        Profile profile = profileService.getProfile(profileId);
        return convertToDto(profile);
    }

    @PostMapping
    @Operation(
            tags = {"Profile"},
            summary = "Create new profile",
            description = "Create new profile",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile to be created",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProfileDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Profile created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProfileDTO.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"img\": \"https://example.com/profile.jpg\",\n" +
                                                    "    \"first_name\": \"John\",\n" +
                                                    "    \"last_name\": \"Doe\",\n" +
                                                    "    \"phone\": \"123-456-7890\",\n" +
                                                    "    \"address\": \"123 Main St\",\n" +
                                                    "    \"city\": \"Exampletown\",\n" +
                                                    "    \"state\": \"Examplestate\",\n" +
                                                    "    \"zipcode\": \"12345\",\n" +
                                                    "    \"available\": true\n" +
                                                    "}"
                                    )
                            )
                    )
            })
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileDTO registerNewProfile(@RequestBody @Valid ProfileDTO profile) {
        return convertToDto(profileService.registerNewProfile(convertToEntity(profile)));
    }

    @DeleteMapping(path = "{profileId}")
    @Operation(
            tags = {"Profile"},
            summary = "Delete a profile",
            description = "Give an id, delete a profile with that id",
            parameters = {
                    @Parameter(
                            name = "profileId",
                            description = "Profile id to be deleted",
                            required = true,
                            example = "1",
                            schema = @Schema(implementation = Integer.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Profile deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiError.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"type\": \"about:blank\",\n" +
                                                    "    \"title\": \"Not Found\",\n" +
                                                    "    \"status\": 404,\n" +
                                                    "    \"detail\": \"Profile with id 72 does not exist\",\n" +
                                                    "    \"instance\": \"/api/v1/profile/72\"\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable("profileId") Long profileId) {
        profileService.deleteProfile(profileId);
    }

    @GetMapping("/{sourceProfileId}/shortest-connection/{targetProfileId}")
    @Operation(
            tags = {"Profile"},
            summary = "Shortest connection",
            description = "Given two ids, returns the shortest connection between them",
            parameters = {
                    @Parameter(
                            name = "sourceProfileId",
                            description = "Starting profile",
                            required = true,
                            example = "1",
                            schema = @Schema(implementation = Long.class)
                    ),
                    @Parameter(
                            name = "targetProfileId",
                            description = "Profile to connect to",
                            required = true,
                            example = "1",
                            schema = @Schema(implementation = Long.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Connection found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = List.class),
                                    examples = @ExampleObject(value = "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Profile cannot be the same"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found"
                    )
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getShortestConnection(@PathVariable Long sourceProfileId, @PathVariable Long targetProfileId) {
        return profileService.getShortestConnection(sourceProfileId, targetProfileId);
    }

    @GetMapping("/{profileId}/friends")
    @Operation(
            tags = {"Profile"},
            summary = "Friends",
            description = "Given an id, returns the friends of that profile",
            parameters = {
                    @Parameter(
                            name = "profileId",
                            description = "Profile id",
                            required = true,
                            example = "1",
                            schema = @Schema(implementation = Long.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Friends found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = List.class),
                                    examples = @ExampleObject(value = "[1, 2]")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found"
                    )
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public List<Long> getFriends(@PathVariable Long profileId) {
        return profileService.getFriends(profileId);
    }

    @PutMapping(path = "{profileId}")
    @Operation(
            tags = {"Profile"},
            summary = "Update profile",
            description = "Update a profile with a given id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile to be updated",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProfileDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile updated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProfileDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Profile cannot be null"
                    )
            },
            parameters = {
                    @Parameter(
                            name = "profileId",
                            description = "Profile id",
                            required = true,
                            example = "1",
                            schema = @Schema(implementation = Long.class)
                    )
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO updateProfile(@PathVariable("profileId") Long profileId, @RequestBody ProfileDTO profile) {
        return convertToDto(profileService.updateProfile(profileId, convertToEntity(profile)));
    }


    private ProfileDTO convertToDto(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        dto.setFirst_name(profile.getFirst_name());
        dto.setLast_name(profile.getLast_name());
        dto.setImg(profile.getImg());
        dto.setAddress(profile.getAddress());
        dto.setCity(profile.getCity());
        dto.setZipcode(profile.getZipcode());
        dto.setPhone(profile.getPhone());
        dto.setAvailable(profile.isAvailable());
        dto.setState(profile.getState());

        return dto;
    }

    private Profile convertToEntity(ProfileDTO profile) {
        return new Profile(profile.getImg(),
                profile.getFirst_name(),
                profile.getLast_name(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getCity(),
                profile.getState(),
                profile.getZipcode(),
                profile.isAvailable());
    }
}
