# Industry Diagnostic Questionnaire System (Australia Deployment)

A research-grade, static, and secure industry diagnostic tool designed for non-commercial academic research.

## Project Structure

```
industry-diagnostic/
├── public/
│   ├── external.html      # Participant Interface (No scoring, strict compliance)
│   ├── internal.html      # Researcher Console (Scoring, IFI, Risk Analysis)
├── config/
│   ├── questionnaire.v1.json # Validated questions, roles, and Likert maps
│   └── scoring.v1.json       # IFI weights, risk thresholds, and logic
├── README.md
└── .gitignore
```

## Setup & Deployment

### 1. Local Development (Required)

Because this system uses external JSON configuration files (`fetch`), modern browsers will block direct file access (`file:///...`) due to CORS security policies.

**You must run a local server:**

1.  Open Terminal / Command Prompt.
2.  Navigate to the project folder:
    ```bash
    cd industry-diagnostic
    ```
3.  Start the Python simple server:
    ```bash
    python -m http.server 8080
    ```
4.  Access the tools in your browser:
    *   **External (Participant):** [http://localhost:8080/public/external.html](http://localhost:8080/public/external.html)
    *   **Internal (Researcher):** [http://localhost:8080/public/internal.html](http://localhost:8080/public/internal.html)

### 2. Workflow

1.  **Distribute:** Send the `external.html` link to industry participants.
2.  **Collect:** Participants complete the form. No data is sent to a server. They download a JSON file (e.g., `response_supplier_173822...json`) and email it to the researcher.
3.  **Analyze:**
    *   Open `internal.html`.
    *   Upload the participant's JSON file.
    *   The system calculates Trust, Rework, Safety scores, and the IFI Index.
    *   Download the "Scored JSON" for aggregation.

## Compliance & Ethics (Hard Requirements)

*   **Voluntary:** Participants can withdraw at any time.
*   **Data Minimisation:** No personal identifiers (names, emails) are required.
*   **Non-Commercial:** Data is strictly for academic research, not for sales or ranking.
*   **Separation of Concerns:** The external tool contains **zero** scoring logic to prevent bias or gaming.

## Configuration Details

### Scoring Logic (Internal Only)

*   **Normalization:** All 5-point Likert scales are mapped to 0–100.
*   **Reverse Coding:** Items marked `reverse: true` are calculated as `100 - Score`.
*   **Missing Values:** Weights are redistributed within the dimension. Missing items are **not** penalized as zero.
*   **IFI Formula:** `0.45 * Trust + 0.35 * Rework + 0.20 * Safety`

### Version Control
*   Questionnaire Version: `v1.0`
*   Scoring Version: `v1.0`

## placeholders to Update
Before final deployment, update the text in `public/external.html`:
*   `[INSERT RETENTION PERIOD]`
*   `[INSERT RESEARCH LEAD NAME]`
*   `[INSERT CONTACT EMAIL]`
