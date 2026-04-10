# Mobile Design System: High-End Appointment Management

## 1. Overview & Creative North Star
### Creative North Star: "The Architectural Ledger"
This design system moves beyond the "app-as-a-tool" mentality to treat appointment management as a high-end editorial experience. We reject the cluttered, boxy aesthetic of traditional utility apps. Instead, we embrace **Architectural Ledgering**: a philosophy that prioritizes structural breathing room, intentional asymmetry, and tonal depth.

By utilizing a refined palette of deep slates and muted blue-grays, the interface establishes an authoritative yet calm environment. We break the "standard template" look by utilizing large-scale display typography alongside meticulously layered surfaces, creating an experience that feels more like a premium digital concierge than a database.

---

## 2. Colors
Our palette is rooted in sophisticated, low-vibrancy tones that minimize eye strain while maximizing professional trust.

### Surface Hierarchy & Nesting
Traditional UI uses lines to separate ideas; we use **Tonal Nesting**.
- **Base Layer:** The `background` (#F7FAFC) serves as our canvas.
- **Sectioning:** Use `surface_container_low` (#EFF4F7) for large logical blocks.
- **The "Nested" Card:** Place `surface_container_lowest` (#FFFFFF) cards inside a `surface_container_low` section. This creates a natural, soft "lift" that feels high-end and structural.

### The "No-Line" Rule
**Explicit Instruction:** Prohibit 1px solid borders for sectioning or containers. High-end design relies on background shifts. If two sections meet, the transition should be defined by the shift from `surface` to `surface_container`, never a stroke.

### The "Glass & Gradient" Rule
- **Floating Elements:** Modals and bottom sheets should utilize `surface` with a 90% opacity and a 20px backdrop-blur.
- **Signature Textures:** For main CTAs and metric highlights, use a subtle linear gradient (Top-Left to Bottom-Right) transitioning from `primary` (#555F71) to `primary_dim` (#495365). This adds a "brushed metal" soul to the UI.

---

## 3. Typography
We use a dual-font strategy to balance editorial authority with functional legibility.

- **Display & Headlines (Manrope):** Chosen for its geometric precision. Use `display-lg` and `headline-md` with slightly tighter letter spacing (-0.02em) to create an "Editorial Header" feel. Headlines should often be left-aligned with generous `spacing-12` top margins to anchor the page.
- **Body & Labels (Inter):** Inter is the workhorse of this system. Its high x-height ensures that appointment details and metric labels remain legible at `body-sm` (0.75rem).
- **Tonal Hierarchy:** Never use pure black for text. Use `on_surface` (#283439) for primary content and `on_surface_variant` (#546166) for secondary metadata.

---

## 4. Elevation & Depth
Depth in this system is a measure of light and layering, not shadow weight.

- **Ambient Shadows:** Standard drop shadows are forbidden. When an element must float (like a FAB), use an extra-diffused shadow: `offset: 0, 8px`, `blur: 24px`, `color: rgba(40, 52, 57, 0.06)`. This mimics natural, ambient room light.
- **The "Ghost Border" Fallback:** In high-density data views where background shifts are insufficient, use a "Ghost Border": `outline_variant` (#A7B4BA) at 15% opacity. It should be felt, not seen.
- **Interaction Depth:** On press, instead of a heavy shadow, "sink" the component by shifting its background color from `surface_container_lowest` to `surface_container_high`.

---

## 5. Components

### Primary Buttons
- **Style:** Pill-shaped (`rounded-full`) with the Signature Gradient (Primary to Primary-Dim).
- **Typography:** `label-md` in `on_primary` (#F6F7FF), uppercase with 0.05em tracking for an authoritative "Admin" feel.
- **Padding:** `1.25rem` horizontal, `0.75rem` vertical.

### Input Fields
- **Container:** `rounded-lg` (0.5rem) with a `surface_container_lowest` fill.
- **Borders:** Use the "Ghost Border" (15% opacity `outline_variant`).
- **State:** On focus, the border shifts to `primary` (#555F71) with a 2px stroke, and the internal icon transitions from `outline` to `primary`.

### Dashboard Metric Cards
- **Structure:** No borders. Use `surface_container_lowest` on a `surface_container_low` background.
- **Asymmetry:** Place the icon in the top-right and the metric value (`headline-lg`) in the bottom-left to create a sophisticated visual diagonal.

### Chips (Status & Filters)
- **Status:** Use low-saturation backgrounds. e.g., "Active" status uses `secondary_container` with `on_secondary_container` text. Avoid "neon" status colors; keep them muted to match the professional slate palette.

### Lists
- **Rule:** Forbid divider lines.
- **Separation:** Use `spacing-4` vertical gaps between list items. Each item is a "cell" defined by a subtle `surface_container_low` hover state or a simple white-space break.

---

## 6. Do's and Don'ts

### Do
- **Do** use `spacing-10` and `spacing-12` to create "Editorial Silences" between major sections.
- **Do** use `Manrope` for all numerical data in dashboard metrics to emphasize the "Ledger" aesthetic.
- **Do** ensure all touch targets are at least 48x48dp, even if the visual element (like a small chip) appears smaller.

### Don't
- **Don't** use 100% black (#000000) or 100% opaque borders. It breaks the "Architectural" softness.
- **Don't** use standard Material Design ripples in high-contrast colors. Use a subtle tonal overlay (2% `on_surface`).
- **Don't** crowd the edges. Maintain a minimum `spacing-6` (1.5rem) "Safe Zone" from the screen edge for all content.