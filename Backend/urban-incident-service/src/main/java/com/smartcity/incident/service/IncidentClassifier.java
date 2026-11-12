package com.smartcity.incident.service;

import com.smartcity.incident.enums.IncidentCategory;
import com.smartcity.incident.enums.Priority;
import org.springframework.stereotype.Component;

/**
 * Lightweight rule-based helper that stands in for AI classification / prioritization.
 * Can later be replaced by a call to an AI microservice.
 */
@Component
public class IncidentClassifier {

    public IncidentCategory suggestCategory(String title, String description) {
        String text = ((title == null ? "" : title) + " " + (description == null ? "" : description)).toLowerCase();
        if (containsAny(text, "pothole", "nid-de-poule", "trou", "asphalt")) {
            return IncidentCategory.POTHOLE;
        }
        if (containsAny(text, "light", "lamp", "streetlight", "éclairage", "lampadaire")) {
            return IncidentCategory.STREETLIGHT;
        }
        if (containsAny(text, "water", "leak", "pipe", "fuite", "canalisation")) {
            return IncidentCategory.WATER_LEAK;
        }
        if (containsAny(text, "graffiti", "tag")) {
            return IncidentCategory.GRAFFITI;
        }
        if (containsAny(text, "waste", "trash", "garbage", "déchet", "ordure")) {
            return IncidentCategory.WASTE;
        }
        if (containsAny(text, "road", "crack", "sidewalk", "chaussée", "trottoir")) {
            return IncidentCategory.ROAD_DAMAGE;
        }
        return IncidentCategory.OTHER;
    }

    public Priority suggestPriority(IncidentCategory category, String title, String description) {
        String text = ((title == null ? "" : title) + " " + (description == null ? "" : description)).toLowerCase();
        if (containsAny(text, "urgent", "danger", "flood", "inondation", "broken glass", "enfant")) {
            return Priority.CRITICAL;
        }
        return switch (category) {
            case WATER_LEAK -> Priority.HIGH;
            case STREETLIGHT, POTHOLE, ROAD_DAMAGE -> Priority.MEDIUM;
            case GRAFFITI, WASTE, OTHER -> Priority.LOW;
        };
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
