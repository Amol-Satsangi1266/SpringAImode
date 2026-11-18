package com.course.SpringAImode.Structured;

import java.util.List;

public record CourseSummary(
        String title,
        String level,
        List<String> learningobjectives,
        int estimatedHours
) {

}
