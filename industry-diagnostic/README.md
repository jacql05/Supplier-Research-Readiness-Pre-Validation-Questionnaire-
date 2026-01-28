# Industry Diagnostic Questionnaire System

A static, configurable industry diagnostic tool assessing Trust, Rework, and Safety.

## Project Structure

```
industry-diagnostic/
├── public/
│   ├── external.html      # Participant interface (Questions)
│   ├── internal.html      # Admin interface (Scoring & Analysis)
│   ├── js/                # (Reserved for future scripts)
│   └── css/               # (Reserved for future styles)
├── config/
│   ├── questionnaire.v1.json # Questions, Roles, Likert mappings
│   └── scoring.v1.json       # Weights, Thresholds, Risk Flags
├── README.md              # Documentation
└── .gitignore
```

## Setup & Usage

### 1. Local Development Environment (Required)

Due to browser security policies (CORS/local file access), you cannot simply open the HTML files directly (e.g., `file:///...`). You must run a local web server.

**Using Python (Recommended):**

1. Open your terminal/command prompt.
2. Navigate to the project root directory:
   ```bash
   cd industry-diagnostic
   ```
3. Start the server:
   ```bash
   python -m http.server 8080
   ```
4. Access the interfaces in your browser:
   - **External (for participants):** [http://localhost:8080/public/external.html](http://localhost:8080/public/external.html)
   - **Internal (for analysis):** [http://localhost:8080/public/internal.html](http://localhost:8080/public/internal.html)

### 2. Workflow

**External (Data Collection):**
1. Send the `external.html` link (or host it) to participants.
2. Participants select their role (Supplier, Production, Venue, Planner, Other).
3. Participants answer 15 questions.
4. On completion, they download a JSON file (e.g., `response_supplier_123456.json`).
5. They send this JSON file to the administrator.

**Internal (Data Analysis):**
1. Administrator opens `internal.html`.
2. Uploads the JSON file received from the participant.
3. The system automatically:
   - Normalizes scores (0-100).
   - Handles reverse-coded items.
   - Calculates dimension scores (Trust, Rework, Safety).
   - Calculates the Industry Friction Index (IFI).
   - Identifies Risk Flags.
   - Suggests Next Actions.
4. Administrator can view results on screen or download the full Scored JSON.

## Configuration

### Questionnaire (`config/questionnaire.v1.json`)
- **Roles:** Define user roles.
- **Likert Maps:** Define scale values (e.g., 1-5 maps to 0-100).
- **Items:** Define questions per role.
  - `reverse: true` indicates the score should be inverted (100 - score).

### Scoring (`config/scoring.v1.json`)
- **Dimensions:** Weights for Trust (0.45), Rework (0.35), Safety (0.20).
- **Thresholds:** Low/High cutoffs for risk levels.
- **Risk Flags:** Logic rules (e.g., `Trust < 40`) and associated messages.

## Changelog

### v1.0 (2026-01-28)
- Initial release.
- Implemented External and Internal interfaces.
- Configurable JSON structure for Questions and Scoring.
- Added IFI calculation and Risk Flag logic.
- Included "Other" role and generic questions.
- Implemented missing value handling (re-weighting within dimension).

## Compliance & Ethics
- **Anonymity:** No personal identifiers collected.
- **Data Minimisation:** No pricing or competitive data requested.
- **Withdrawal:** Participants can withdraw before submission.
