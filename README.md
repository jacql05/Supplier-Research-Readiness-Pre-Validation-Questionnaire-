# Industry Diagnostic Questionnaire System (Australia Deployment)

A research-grade, static, and secure industry diagnostic tool designed for non-commercial academic research.

## Project Structure

```
/ (Root Directory)
├── public/
│   ├── external.html      # Participant Interface (No scoring, strict compliance)
│   ├── internal.html      # Researcher Console (Scoring, IFI, Risk Analysis)
├── config/
│   ├── questionnaire.v1.json # Validated questions, roles, and Likert maps
│   └── scoring.v1.json       # IFI weights, risk thresholds, and logic
└── README.md
```

## How to Use

### 1. Online Use (Recommended) — Participants
**Goal:** Complete the questionnaire and submit raw data.

**Do NOT run any code.** Simply access the live link:

1.  **Access:** Open the official research link (provided by email).
    *   *GitHub Pages URL:* `https://<your-username>.github.io/<repo-name>/public/external.html`
    *   *(e.g., https://jacql05.github.io/Supplier-Research-Readiness-Pre-Validation-Questionnaire-/public/external.html)*
2.  **Complete:** Select your role and answer the 15 questions.
3.  **Submit:** Click "Complete & Download" to get your `response_*.json` file.
4.  **Send:** Email the file to: `eghire.contact@gmail.com`.

---

### 2. Offline Use (Optional) — Researchers
**Goal:** Score the response files securely without uploading data to any external server.

Because this system uses external JSON configuration files (`fetch`), modern browsers will block direct file access (`file:///...`) due to CORS security policies. If you are running offline, you must use a local server.

1.  Open Terminal / Command Prompt.
2.  Navigate to the **project root directory** (where this README is located):
    ```bash
    cd <path-to-repo-root>
    ```
3.  Start the local server:
    ```bash
    python -m http.server 8080
    ```
4.  Open the **Internal Console**: [http://localhost:8080/public/internal.html](http://localhost:8080/public/internal.html)
5.  **Analyze:** Upload the participant’s JSON file to generate scores (Trust/Rework/Safety) and Risk Flags.

---

## Deployment Guide (GitHub Pages)

To make the **External Interface** accessible to participants:

1.  Push this code to your GitHub repository (main branch).
2.  Go to **Settings** -> **Pages**.
3.  Under **Source**, select `Deploy from a branch`.
4.  Select Branch: `main` (or the current working branch) / Folder: `/(root)`.
5.  Click **Save**.
6.  Wait ~1 minute. Your live site will be at the URL shown in the settings (append `/public/external.html`).

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

## Placeholders to Update (Before final deployment)
Before final deployment, update the text in `public/external.html`:
*   `5 years from publication`
*   `Jacq Lew, Project Lead (College-led applied research initiative)`
*   `eghire.contact@gmail.com`
