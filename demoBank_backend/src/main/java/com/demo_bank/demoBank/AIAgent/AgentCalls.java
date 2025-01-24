package com.demo_bank.demoBank.AIAgent;



import com.demo_bank.demoBank.DAOmodel.Transaction;
import com.demo_bank.demoBank.DTO.ChatRequest;
import com.demo_bank.demoBank.DTO.DialogflowResponseDTO;
import com.demo_bank.demoBank.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.dialogflow.v2.*;
import com.google.protobuf.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.protobuf.Struct;


import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.TextInput.Builder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/agent/webhook/")
public class AgentCalls {

    private static final String PROJECT_ID = "mia-qpuf";
    private static final String CREDENTIALS_PATH = "C:\\Users\\user\\Downloads\\mia-qpuf-4aa8077e607c.json";

    @Autowired
    private TransactionService transactionService;


    private String transactionId;
    private String inputIntent = "";


    @PostMapping(path = "/")
    public Object handleDialogflowRequest(@RequestBody DialogflowResponseDTO dialogflowResponseDTO) throws IOException {
        if (Objects.nonNull(dialogflowResponseDTO.getQueryResult())) {
            String intent = dialogflowResponseDTO.getQueryResult().getIntent().getDisplayName();
            if (intent.equalsIgnoreCase("give transRef-context:transaction failed")) {
                inputIntent = "/contexts/initiate-reversal";
                return getTransactionByRef(dialogflowResponseDTO);
            } else if (intent.equalsIgnoreCase("initiate-reversal: context> initiate a reversal")) {
                return performReversal(dialogflowResponseDTO);
            } else if (intent.equalsIgnoreCase("submit dispute")) {
                return submitDispute(dialogflowResponseDTO);
            }
        }
        return ResponseEntity.badRequest().body("Invalid request");
    }


    private Object getTransactionByRef(DialogflowResponseDTO dialogflowResponseDTO) throws JsonProcessingException {
        transactionId = dialogflowResponseDTO.getQueryResult().getParameters().getTransactionID();
        Transaction transactionByRef = transactionService.getTransactionByRef(transactionId);
        String status = transactionByRef.getStatus();
        String amount = String.valueOf(transactionByRef.getAmount());
        String date = transactionByRef.getDate();
        String time = transactionByRef.getTime();

        String formattedResponse = "<div style='font-family: Arial, sans-serif; border: 1px solid #ccc; padding: 10px; width: 300px;'>"
                + "<h3 style='text-align: center; color: #333;'>Transaction Information</h3>"
                + "<p><strong>Amount:</strong> " + amount + " Naira</p>"
                + "<p><strong>Status:</strong> " + status + "</p>"
                + "<p><strong>Date:</strong> " + date + "</p>"
                + "<p><strong>Time:</strong> " + time + "</p>"
                + "<hr style='border: 0; border-top: 1px solid #ccc;'/>"
                + "<p style='text-align: center;'>Would you like me to initiate a reversal?</p>"
                + "</div>";


        sendMessageToDialogflowBackend("Transaction details retrieved successfully.");

        Map<String, Object> response = new HashMap<>();
        response.put("fulfillmentText", formattedResponse);
        return ResponseEntity.ok(response);
    }

    private Object performReversal(DialogflowResponseDTO dialogflowResponseDTO) {
        String response = transactionService.preformReversal(transactionId);
        String text;

        if (response.equalsIgnoreCase("success")) {
            text = "Done! You should be credited back the money soon.";
        } else {
            text = "Unable to perform a reversal. Would you like to submit a dispute?";
        }

        sendMessageToDialogflowBackend("Reversal attempted: " + response);

        Map<String, Object> dialogFlowResponse = new HashMap<>();
        dialogFlowResponse.put("fulfillmentText", text);
        return ResponseEntity.ok(dialogFlowResponse);
    }

    private Object submitDispute(DialogflowResponseDTO dialogflowResponseDTO) {
        transactionService.submitDispute(transactionId);
        String text = "Your transaction has been submitted. It will be resolved soon.";

        Map<String, Object> dialogFlowResponse = new HashMap<>();
        dialogFlowResponse.put("fulfillmentText", text);
        return ResponseEntity.ok(dialogFlowResponse);
    }

    private void sendMessageToDialogflowBackend(String message) {
        // Implement logic to send updates to Dialogflow or log as needed
        log.info("Sending to Dialogflow: {}", message);
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody ChatRequest chatRequest) {
        String requestText = chatRequest.getUserMessage();
        String responseText = "";

        try {
            // Load the service account key
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH));
            SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            // Create a session client
            try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
                // Define the session ID and session path
                String sessionId = UUID.randomUUID().toString();
                String locationId = "global";
                String sessionPath = String.format(
                        "projects/%s/locations/%s/agent/sessions/%s",
                        PROJECT_ID,
                        locationId,
                        sessionId
                );

                // Create a text input with the message "hey"
                Builder textInput = TextInput.newBuilder().setText(requestText).setLanguageCode("en-US");

                // Create intent parameters (e.g., adding additional inputs)
                Struct.Builder parameters = Struct.newBuilder();
                parameters.putFields("customParam", Value.newBuilder().setStringValue("additional info").build());

                Context.Builder contextBuilder;
                QueryParameters queryParameters;
                if(!inputIntent.isBlank()){
                    contextBuilder = Context.newBuilder()
                            .setName(sessionPath + inputIntent)
                            .setLifespanCount(5);

                    // Create query parameters and add the input parameters
                    queryParameters = QueryParameters.newBuilder()
                            .addContexts(contextBuilder.build())
                            .setPayload(parameters.build())
                            .build();
                }else{
                    queryParameters = QueryParameters.newBuilder()
                            .setPayload(parameters.build())
                            .build();
                }

                // Create the query input
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                // Send the request to Dialogflow
                DetectIntentRequest request = DetectIntentRequest.newBuilder()
                        .setSession(sessionPath)
                        .setQueryInput(queryInput)
                        .setQueryParams(queryParameters)
                        .build();

                // Send the request to Dialogflow
                DetectIntentResponse response = sessionsClient.detectIntent(request);
                QueryResult queryResult = response.getQueryResult();
                log.info("--> {}", queryResult);

                // Extract the response text
                responseText = queryResult.getFulfillmentText();
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }

        return responseText;
    }
}


