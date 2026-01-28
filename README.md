# Industry Diagnostic Questionnaire System (Australia Deployment)

A research-grade, static, and secure industry diagnostic tool designed for non-commercial academic research.

## Project Structure

```
/
├── public/
│   ├── external.html      # Participant Interface (No scoring, strict compliance)
│   ├── internal.html      # Researcher Console (Scoring, IFI, Risk Analysis)
├── config/
│   ├── questionnaire.v1.json # Validated questions, roles, and Likert maps
│   └── scoring.v1.json       # IFI weights, risk thresholds, and logic
└── README.md
```

## How to Use

### 1. For Participants (Industry)
**Goal:** Complete the questionnaire and submit raw data.

1.  **Access the Form:** Open the official research link (provided by email).
    *   *Example:* `https://jacql05.github.io/Supplier-Research-Readiness-Pre-Validation-Questionnaire-/public/external.html`
2.  **Complete:** Select your role and answer the 15 questions.
3.  **Submit:** Click "Complete & Download".
    *   This generates a `response_*.json` file on your device.
    *   **Note:** No data is stored on any server.
4.  **Send:** Email the downloaded file to: `eghire.contact@gmail.com`.

### 2. For Researchers (Internal Analysis)
**Goal:** Score the response files securely.

**Recommended:** Run the scoring tool locally to ensure data privacy (Score Logic is hidden from the public web).

1.  Open Terminal / Command Prompt in the project folder.
2.  Start the local server:
    ```bash
    python -m http.server 8080
    ```
3.  Open the **Internal Console**: [http://localhost:8080/public/internal.html](http://localhost:8080/public/internal.html)
4.  **Analyze:**
    *   Upload the participant’s `response_*.json` file.
    *   View calculated **Trust**, **Rework**, **Safety** scores and **IFI Index**.
    *   Check **Risk Flags** and **Next Actions**.
    *   Download the full "Scored JSON" for your records.

## Deployment Guide

### Option A: GitHub Pages (Free & Fast)
1.  Push this code to your GitHub repository.
2.  Go to **Settings** -> **Pages**.
3.  Under **Source**, select `Deploy from a branch`.
4.  Select Branch: `main` (or master) / Folder: `/(root)`.
5.  Click **Save**.
6.  Your External URL will be: `https://<username>.github.io/<repo-name>/public/external.html`

### Option B: Custom Domain (Professional)
To use a domain like `diagnostic.eghire.com`:
1.  Configure your DNS provider (add CNAME pointing to `<username>.github.io`).
2.  In GitHub **Settings** -> **Pages** -> **Custom domain**, enter your domain.
3.  Check **Enforce HTTPS**.

## Compliance & Ethics (Hard Requirements)

*   **Voluntary:** Participants can withdraw at any time.
*   **Data Minimisation:** No personal identifiers (names, emails) are required.
*   **Non-Commercial:** Data is strictly for academic research, not for sales or ranking.
*   **Separation of Concerns:** The external tool contains **zero** scoring logic to prevent bias or gaming.

## Configuration Details

### Scoring Logic (Internal Only)
*   **Normalization:** All 5-point Likert scales are mapped to 0–100.
*   **Reverse Coding:** Items marked `reverse: true` are calculated as `100 - Score`.
*   **Missing Values:** Weights are redistributed within the dimension. Missing items are not penalized as zero.
*   **IFI Formula:** `0.45 * Trust + 0.35 * Rework + 0.20 * Safety`

### Version Control
*   Questionnaire Version: `v1.0`
*   Scoring Version: `v1.0`
