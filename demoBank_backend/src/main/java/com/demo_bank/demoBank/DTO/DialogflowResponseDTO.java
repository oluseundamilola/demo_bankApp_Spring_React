package com.demo_bank.demoBank.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DialogflowResponseDTO {

    private String responseId;

    private QueryResult queryResult;

    private WebhookStatus webhookStatus;

    @Getter
    @Setter
    public static class QueryResult {
        private String queryText;
        private Parameters parameters;
        private boolean allRequiredParamsPresent;
        private String fulfillmentText;
        private List<FulfillmentMessage> fulfillmentMessages;
        private List<OutputContext> outputContexts;
        private Intent intent;
        private double intentDetectionConfidence;
        private DiagnosticInfo diagnosticInfo;
        private String languageCode;
    }

    @Getter
    @Setter
    public static class FulfillmentMessage {
        private Text text;
    }

    @Getter
    @Setter
    public static class Text {
        private List<String> text;
    }

    @Getter
    @Setter
    public static class OutputContext {
        private String name;
        private int lifespanCount;
    }

    @Getter
    @Setter
    public static class Intent {
        private String name;
        private String displayName;
    }

    @Getter
    @Setter
    public static class DiagnosticInfo {
        private int webhookLatencyMs;
    }

    @Getter
    @Setter
    public static class WebhookStatus {
        private int code;
        private String message;
    }

    @Getter
    @Setter
    public static class Parameters {
        private String transactionID;
    }
}

