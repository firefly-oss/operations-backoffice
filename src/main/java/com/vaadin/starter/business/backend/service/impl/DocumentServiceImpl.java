package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.documentmanagement.AuthorizationDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentTrackingEntryDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.SignatureDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.TransactionDocumentDTO;
import com.vaadin.starter.business.backend.service.DocumentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the DocumentService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private final Random random = new Random(42); // Fixed seed for reproducible data
    private final Map<String, DocumentDTO> documents = new HashMap<>();
    private final Map<String, DocumentTrackingEntryDTO> documentTrackingEntries = new HashMap<>();
    private final Map<String, SignatureDTO> signatures = new HashMap<>();
    private final Map<String, AuthorizationDTO> authorizations = new HashMap<>();
    private final Map<String, TransactionDocumentDTO> transactionDocuments = new HashMap<>();

    public DocumentServiceImpl() {
        generateMockDocuments();
        generateMockDocumentTrackingEntries();
        generateMockSignatures();
        generateMockAuthorizations();
        generateMockTransactionDocuments();
    }

    /**
     * Get all documents.
     *
     * @return Collection of all documents
     */
    @Override
    public Collection<DocumentDTO> getDocuments() {
        return documents.values();
    }

    /**
     * Get a document by ID.
     *
     * @param id Document ID
     * @return Document with the given ID, or null if not found
     */
    @Override
    public DocumentDTO getDocumentById(String id) {
        return documents.get(id);
    }

    /**
     * Get all document tracking entries.
     *
     * @return Collection of all document tracking entries
     */
    @Override
    public Collection<DocumentTrackingEntryDTO> getDocumentTrackingEntries() {
        return documentTrackingEntries.values();
    }

    /**
     * Get a document tracking entry by ID.
     *
     * @param id Document tracking entry ID
     * @return Document tracking entry with the given ID, or null if not found
     */
    @Override
    public DocumentTrackingEntryDTO getDocumentTrackingEntryById(String id) {
        return documentTrackingEntries.get(id);
    }

    /**
     * Get all signatures.
     *
     * @return Collection of all signatures
     */
    @Override
    public Collection<SignatureDTO> getSignatures() {
        return signatures.values();
    }

    /**
     * Get a signature by ID.
     *
     * @param id Signature ID
     * @return Signature with the given ID, or null if not found
     */
    @Override
    public SignatureDTO getSignatureById(String id) {
        return signatures.get(id);
    }

    /**
     * Get all authorizations.
     *
     * @return Collection of all authorizations
     */
    @Override
    public Collection<AuthorizationDTO> getAuthorizations() {
        return authorizations.values();
    }

    /**
     * Get an authorization by ID.
     *
     * @param id Authorization ID
     * @return Authorization with the given ID, or null if not found
     */
    @Override
    public AuthorizationDTO getAuthorizationById(String id) {
        return authorizations.get(id);
    }

    /**
     * Get all transaction documents.
     *
     * @return Collection of all transaction documents
     */
    @Override
    public Collection<TransactionDocumentDTO> getTransactionDocuments() {
        return transactionDocuments.values();
    }

    /**
     * Get a transaction document by ID.
     *
     * @param id Transaction document ID
     * @return Transaction document with the given ID, or null if not found
     */
    @Override
    public TransactionDocumentDTO getTransactionDocumentById(String id) {
        return transactionDocuments.get(id);
    }

    /**
     * Generate mock document data.
     */
    private void generateMockDocuments() {
        // Clear existing documents
        documents.clear();

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] documentTypes = getDocumentTypes();
        String[] statuses = getStatuses();
        String[] fileExtensions = {".pdf", ".jpg", ".png", ".docx", ".xlsx"};

        String[] uploadedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] descriptions = {
            "Customer identification document",
            "Proof of residence",
            "Financial statement for loan application",
            "Supporting document for account opening",
            "Required document for KYC compliance",
            "Additional verification document",
            "Document submitted for account update",
            "Mandatory regulatory document"
        };

        for (int i = 1; i <= 50; i++) {
            String documentId = "DOC" + String.format("%06d", i);

            int customerIndex = random.nextInt(customerIds.length);
            String customerId = customerIds[customerIndex];
            String customerName = customerNames[customerIndex];

            String documentType = documentTypes[random.nextInt(documentTypes.length)];
            String status = statuses[random.nextInt(statuses.length)];

            // Generate a filename based on document type
            String fileExtension = fileExtensions[random.nextInt(fileExtensions.length)];
            String filename = documentType.replace(" ", "_").toLowerCase() + "_" + 
                            customerId.toLowerCase() + "_" + 
                            (random.nextInt(900) + 100) + fileExtension;

            // Generate a random upload date within the last 180 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime uploadDate = now.minusDays(random.nextInt(180)).minusHours(random.nextInt(24));

            // Generate a random file size between 100KB and 10MB
            String fileSize = formatFileSize(100 + random.nextInt(9900));

            String uploadedByPerson = uploadedBy[random.nextInt(uploadedBy.length)];
            String description = descriptions[random.nextInt(descriptions.length)];

            // Create document DTO
            DocumentDTO document = new DocumentDTO(
                documentId,
                customerId,
                customerName,
                documentType,
                status,
                filename,
                uploadDate,
                fileSize,
                uploadedByPerson,
                description
            );

            // Add to map
            documents.put(documentId, document);
        }
    }

    /**
     * Generate mock document tracking entries.
     */
    private void generateMockDocumentTrackingEntries() {
        // Clear existing entries
        documentTrackingEntries.clear();

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] documentTypes = {"ID Document", "Contract", "Invoice", "Statement", "Other"};
        String[] statuses = {
            DocumentTrackingEntryDTO.Status.PENDING.getName(),
            DocumentTrackingEntryDTO.Status.PROCESSING.getName(),
            DocumentTrackingEntryDTO.Status.COMPLETED.getName(),
            DocumentTrackingEntryDTO.Status.REJECTED.getName()
        };
        String[] stages = {
            DocumentTrackingEntryDTO.Stage.DOCUMENT_SUBMISSION.getName(), 
            DocumentTrackingEntryDTO.Stage.INITIAL_VERIFICATION.getName(), 
            DocumentTrackingEntryDTO.Stage.CONTENT_VALIDATION.getName(), 
            DocumentTrackingEntryDTO.Stage.APPROVAL_PROCESS.getName(), 
            DocumentTrackingEntryDTO.Stage.FINAL_REVIEW.getName(),
            DocumentTrackingEntryDTO.Stage.ARCHIVING.getName(),
            DocumentTrackingEntryDTO.Stage.REJECTED.getName()
        };

        String[] assignedTo = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] notes = {
            "Document submitted via online portal",
            "Waiting for customer verification",
            "Document requires additional information",
            "Processing delayed due to high volume",
            "Document approved by compliance team",
            "Rejected due to missing information",
            "Customer notified of document status",
            "Document archived in system"
        };

        for (int i = 1; i <= 50; i++) {
            String documentId = "DOC" + String.format("%06d", i);
            String customerId = customerIds[random.nextInt(customerIds.length)];
            String documentType = documentTypes[random.nextInt(documentTypes.length)];

            String status = statuses[random.nextInt(statuses.length)];

            // Generate submission date within the last 30 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime submissionDate = now.minusDays(random.nextInt(30)).minusHours(random.nextInt(24));

            // Generate last updated date after submission date
            LocalDateTime lastUpdated = submissionDate.plusHours(random.nextInt(24 * 30));
            if (lastUpdated.isAfter(now)) {
                lastUpdated = now;
            }

            // Set current stage based on status
            String currentStage;
            if (status.equals(DocumentTrackingEntryDTO.Status.PENDING.getName())) {
                currentStage = stages[0];
            } else if (status.equals(DocumentTrackingEntryDTO.Status.PROCESSING.getName())) {
                currentStage = stages[1 + random.nextInt(3)];
            } else if (status.equals(DocumentTrackingEntryDTO.Status.COMPLETED.getName())) {
                currentStage = stages[5];
            } else {
                currentStage = stages[6];
            }

            String assignedToPerson = assignedTo[random.nextInt(assignedTo.length)];

            // Set estimated completion date
            String estimatedCompletion;
            if (status.equals(DocumentTrackingEntryDTO.Status.PENDING.getName()) || 
                status.equals(DocumentTrackingEntryDTO.Status.PROCESSING.getName())) {
                int daysToAdd = 1 + random.nextInt(14);
                estimatedCompletion = now.plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                estimatedCompletion = "N/A";
            }

            String note = notes[random.nextInt(notes.length)];

            // Create document tracking entry DTO
            DocumentTrackingEntryDTO entry = new DocumentTrackingEntryDTO(
                documentId,
                customerId,
                documentType,
                status,
                submissionDate,
                lastUpdated,
                currentStage,
                assignedToPerson,
                estimatedCompletion,
                note
            );

            // Add to map
            documentTrackingEntries.put(documentId, entry);
        }
    }

    /**
     * Generate mock signatures.
     */
    private void generateMockSignatures() {
        // Clear existing signatures
        signatures.clear();

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] signatureTypes = {
            SignatureDTO.SignatureType.ELECTRONIC.getName(),
            SignatureDTO.SignatureType.DIGITAL.getName(),
            SignatureDTO.SignatureType.HANDWRITTEN.getName(),
            SignatureDTO.SignatureType.BIOMETRIC.getName(),
            SignatureDTO.SignatureType.QUALIFIED_ELECTRONIC.getName()
        };

        String[] statuses = {
            SignatureDTO.Status.ACTIVE.getName(),
            SignatureDTO.Status.EXPIRED.getName(),
            SignatureDTO.Status.REVOKED.getName(),
            SignatureDTO.Status.PENDING_VERIFICATION.getName(),
            SignatureDTO.Status.VERIFIED.getName()
        };

        String[] verifiedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] notes = {
            "Signature verified by compliance team",
            "Signature pending verification",
            "Signature expired and needs renewal",
            "Signature revoked due to security concerns",
            "Signature used for account opening",
            "Signature used for loan application",
            "Signature used for transaction authorization",
            "Signature used for document signing"
        };

        for (int i = 1; i <= 50; i++) {
            String signatureId = "SIG" + String.format("%06d", i);

            int customerIndex = random.nextInt(customerIds.length);
            String customerId = customerIds[customerIndex];
            String customerName = customerNames[customerIndex];

            String signatureType = signatureTypes[random.nextInt(signatureTypes.length)];
            String status = statuses[random.nextInt(statuses.length)];

            // Generate creation date within the last 365 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime creationDate = now.minusDays(random.nextInt(365)).minusHours(random.nextInt(24));

            // Generate expiry date (1-3 years after creation date)
            LocalDateTime expiryDate = creationDate.plusYears(1 + random.nextInt(2));

            String verifiedByPerson = verifiedBy[random.nextInt(verifiedBy.length)];
            String note = notes[random.nextInt(notes.length)];

            // Create signature DTO
            SignatureDTO signature = new SignatureDTO(
                signatureId,
                customerId,
                customerName,
                signatureType,
                status,
                creationDate,
                expiryDate,
                verifiedByPerson,
                note
            );

            // Add to map
            signatures.put(signatureId, signature);
        }
    }

    /**
     * Generate mock authorizations.
     */
    private void generateMockAuthorizations() {
        // Clear existing authorizations
        authorizations.clear();

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] authorizationTypes = {
            AuthorizationDTO.AuthorizationType.ACCOUNT_ACCESS.getName(),
            AuthorizationDTO.AuthorizationType.TRANSACTION_APPROVAL.getName(),
            AuthorizationDTO.AuthorizationType.DOCUMENT_SIGNING.getName(),
            AuthorizationDTO.AuthorizationType.INFORMATION_DISCLOSURE.getName(),
            AuthorizationDTO.AuthorizationType.POWER_OF_ATTORNEY.getName()
        };

        String[] statuses = {
            AuthorizationDTO.Status.ACTIVE.getName(),
            AuthorizationDTO.Status.EXPIRED.getName(),
            AuthorizationDTO.Status.REVOKED.getName(),
            AuthorizationDTO.Status.PENDING_APPROVAL.getName(),
            AuthorizationDTO.Status.APPROVED.getName()
        };

        String[] authorizedTo = {
            "Jane Smith", 
            "Carlos Garcia", 
            "Li Chen", 
            "Emily Johnson", 
            "Mohammed Hassan",
            "James Wilson",
            "Jennifer Brown",
            "Aisha Al-Farsi"
        };

        String[] approvedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] notes = {
            "Authorization for account access",
            "Authorization for transaction approval",
            "Authorization for document signing",
            "Authorization for information disclosure",
            "Power of attorney for financial matters",
            "Limited authorization for specific transactions",
            "Temporary authorization for account access",
            "Authorization for third-party account management"
        };

        for (int i = 1; i <= 50; i++) {
            String authorizationId = "AUTH" + String.format("%06d", i);

            int customerIndex = random.nextInt(customerIds.length);
            String customerId = customerIds[customerIndex];
            String customerName = customerNames[customerIndex];

            String authorizationType = authorizationTypes[random.nextInt(authorizationTypes.length)];
            String status = statuses[random.nextInt(statuses.length)];
            String authorizedToPerson = authorizedTo[random.nextInt(authorizedTo.length)];

            // Generate creation date within the last 365 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime creationDate = now.minusDays(random.nextInt(365)).minusHours(random.nextInt(24));

            // Generate expiry date (1-3 years after creation date)
            LocalDateTime expiryDate = creationDate.plusYears(1 + random.nextInt(2));

            String approvedByPerson = approvedBy[random.nextInt(approvedBy.length)];
            String note = notes[random.nextInt(notes.length)];

            // Create authorization DTO
            AuthorizationDTO authorization = new AuthorizationDTO(
                authorizationId,
                customerId,
                customerName,
                authorizationType,
                status,
                authorizedToPerson,
                creationDate,
                expiryDate,
                approvedByPerson,
                note
            );

            // Add to map
            authorizations.put(authorizationId, authorization);
        }
    }

    /**
     * Generate mock transaction documents.
     */
    private void generateMockTransactionDocuments() {
        // Clear existing transaction documents
        transactionDocuments.clear();

        String[] transactionIds = {"T10045", "T20056", "T30078", "T40023", "T50091", "T60112", "T70134", "T80156"};
        String[] transactionTypes = {
            TransactionDocumentDTO.TransactionType.PAYMENT.getName(),
            TransactionDocumentDTO.TransactionType.TRANSFER.getName(),
            TransactionDocumentDTO.TransactionType.DEPOSIT.getName(),
            TransactionDocumentDTO.TransactionType.WITHDRAWAL.getName(),
            TransactionDocumentDTO.TransactionType.LOAN.getName(),
            TransactionDocumentDTO.TransactionType.INVESTMENT.getName(),
            TransactionDocumentDTO.TransactionType.FOREIGN_EXCHANGE.getName(),
            TransactionDocumentDTO.TransactionType.OTHER.getName()
        };

        String[] documentTypes = {
            TransactionDocumentDTO.DocumentType.INVOICE.getName(),
            TransactionDocumentDTO.DocumentType.RECEIPT.getName(),
            TransactionDocumentDTO.DocumentType.CONTRACT.getName(),
            TransactionDocumentDTO.DocumentType.AGREEMENT.getName(),
            TransactionDocumentDTO.DocumentType.STATEMENT.getName(),
            TransactionDocumentDTO.DocumentType.AUTHORIZATION.getName(),
            TransactionDocumentDTO.DocumentType.CONFIRMATION.getName(),
            TransactionDocumentDTO.DocumentType.OTHER.getName()
        };

        String[] statuses = {
            TransactionDocumentDTO.Status.PENDING_REVIEW.getName(),
            TransactionDocumentDTO.Status.APPROVED.getName(),
            TransactionDocumentDTO.Status.REJECTED.getName(),
            TransactionDocumentDTO.Status.EXPIRED.getName(),
            TransactionDocumentDTO.Status.NEEDS_UPDATE.getName()
        };

        String[] fileExtensions = {".pdf", ".jpg", ".png", ".docx", ".xlsx"};

        String[] uploadedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] descriptions = {
            "Transaction invoice document",
            "Receipt for transaction",
            "Contract for transaction",
            "Agreement for transaction",
            "Statement for transaction",
            "Authorization for transaction",
            "Confirmation of transaction",
            "Supporting document for transaction"
        };

        for (int i = 1; i <= 50; i++) {
            String documentId = "TDOC" + String.format("%06d", i);
            String transactionId = transactionIds[random.nextInt(transactionIds.length)];
            String transactionType = transactionTypes[random.nextInt(transactionTypes.length)];
            String documentType = documentTypes[random.nextInt(documentTypes.length)];
            String status = statuses[random.nextInt(statuses.length)];

            // Generate a filename based on document type
            String fileExtension = fileExtensions[random.nextInt(fileExtensions.length)];
            String filename = documentType.replace(" ", "_").toLowerCase() + "_" + 
                            transactionId.toLowerCase() + "_" + 
                            (random.nextInt(900) + 100) + fileExtension;

            // Generate a random upload date within the last 180 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime uploadDate = now.minusDays(random.nextInt(180)).minusHours(random.nextInt(24));

            // Generate a random file size between 100KB and 10MB
            String fileSize = formatFileSize(100 + random.nextInt(9900));

            String uploadedByPerson = uploadedBy[random.nextInt(uploadedBy.length)];
            String description = descriptions[random.nextInt(descriptions.length)];

            // Create transaction document DTO
            TransactionDocumentDTO document = new TransactionDocumentDTO(
                documentId,
                transactionId,
                transactionType,
                documentType,
                status,
                filename,
                uploadDate,
                fileSize,
                uploadedByPerson,
                description
            );

            // Add to map
            transactionDocuments.put(documentId, document);
        }
    }

    private String[] getDocumentTypes() {
        return new String[] {
            "ID Document", 
            "Proof of Address", 
            "Income Statement", 
            "Tax Return", 
            "Bank Statement",
            "Employment Verification",
            "Signature Card",
            "Contract",
            "Application Form",
            "Power of Attorney"
        };
    }

    private String[] getStatuses() {
        return new String[] {
            "Pending Review", 
            "Approved", 
            "Rejected", 
            "Expired", 
            "Needs Update"
        };
    }

    private String formatFileSize(int sizeInKB) {
        if (sizeInKB < 1024) {
            return sizeInKB + " KB";
        } else {
            double sizeInMB = sizeInKB / 1024.0;
            return String.format("%.2f MB", sizeInMB);
        }
    }
}
