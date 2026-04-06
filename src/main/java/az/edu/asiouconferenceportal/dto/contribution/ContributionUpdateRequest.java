package az.edu.asiouconferenceportal.dto.contribution;

import az.edu.asiouconferenceportal.common.enums.ContributionRole;
import az.edu.asiouconferenceportal.common.enums.SpeechType;
import az.edu.asiouconferenceportal.common.enums.TimeScope;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

import lombok.Data;

@Data
public class ContributionUpdateRequest {
    @NotEmpty
    private Set<ContributionRole> roles;
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String title;
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String keywords;
    @NotBlank
    @jakarta.validation.constraints.Size(max = 4000)
    private String description;
    @NotBlank
    @jakarta.validation.constraints.Size(max = 2000)
    private String bio;
    @NotNull
    private SpeechType speechType;
    @NotNull
    private TimeScope timeScope;
    @NotBlank
    @jakarta.validation.constraints.Size(max = 255)
    private String audience;
    @jakarta.validation.constraints.Size(max = 512)
    @org.hibernate.validator.constraints.URL
    private String previousTalkUrl;
}
