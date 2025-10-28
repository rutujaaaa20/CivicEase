package sk.kasv.babcak.civicease.dto;

import lombok.Data;
import sk.kasv.babcak.civicease.models.Report.ReportPriority;

@Data
public class CreateReportRequest {
    private String title;
    private String description;
    private String location;
    private Long citizenId;
    private Long departmentId;
    private ReportPriority priority;
}
