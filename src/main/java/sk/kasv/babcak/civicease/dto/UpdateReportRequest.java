package sk.kasv.babcak.civicease.dto;

import lombok.Data;
import sk.kasv.babcak.civicease.models.Report.ReportPriority;
import sk.kasv.babcak.civicease.models.Report.ReportStatus;

@Data
public class UpdateReportRequest {
    private String title;
    private String description;
    private String location;
    private ReportStatus status;
    private ReportPriority priority;
    private Long departmentId;
}
