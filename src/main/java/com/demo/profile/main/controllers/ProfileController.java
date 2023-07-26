package com.demo.profile.main.controllers;


import com.demo.profile.main.model.profile.Profile;
import com.demo.profile.main.model.profile.ProfilePage;
import com.demo.profile.main.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;
    int pageSize;

    @Autowired
    public ProfileController(ProfileService profileService,
                             @Value("${config.pageSize}") int pageSize) {
        this.profileService = profileService;
        this.pageSize = pageSize;
    }

    @GetMapping
    @Operation(
            tags = {"Profile"},
            summary = "Get all profiles",
            description = "Get all profiles",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number to be loaded",
                            required = true,
                            example = "1",
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
    public ResponseEntity<?> getAllProfiles(@RequestParam int page) {
        return profileService.getAllProfiles(Math.max(0, page - 1), pageSize);
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
                                    schema = @Schema(implementation = Profile.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            })
    public ResponseEntity<?> getProfileById(@PathVariable("profileId") Long profileId) {
        return profileService.getProfile(profileId);
    }

    @PostMapping
    @Operation(
            tags = {"Profile"},
            summary = "Add new profile",
            description = "Add a new profile",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile to be added",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Profile.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile added",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Profile already exists",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            })
    public ResponseEntity<String> registerNewProfile(@RequestBody Profile profile) {
        return profileService.registerNewProfile(profile);
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
                            responseCode = "200",
                            description = "Profile deleted",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    public ResponseEntity<String> deleteProfile(@PathVariable("profileId") Long profileId) {
        return profileService.deleteProfile(profileId);
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
    public ResponseEntity<?> getShortestConnection(@PathVariable Long sourceProfileId, @PathVariable Long targetProfileId) {
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
    public ResponseEntity<?> getFriends(@PathVariable Long profileId) {
        return profileService.getFriends(profileId);
    }

    @PutMapping(path = "{profileId}")
    @Operation(
            tags = {"Profile"},
            summary = "Update profile",
            description = "Update a profile only if the profile exists and if there are any changes to update:\n" +
                    "1. The profile must exist in the system.\n" +
                    "2. There must be some changes to update. This means that the update operation will only be performed" +
                    " if there are modifications to any of the profile's fields. If there are no changes, the update operation should not be executed.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile to be updated",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Profile.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile updated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Profile.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Profile not found"
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
    public ResponseEntity<?> updateProfile(@PathVariable("profileId") Long profileId, @RequestBody Profile profile) {
        if (profile == null) {
            return new ResponseEntity<>("Profile cannot be null", HttpStatus.BAD_REQUEST);
        }
        return profileService.updateStudent(profileId,
                profile.getImg(),
                profile.getFirst_name(),
                profile.getLast_name(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getCity(),
                profile.getState(),
                profile.getZipcode());
    }
}
