package com.bms.app.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ── Surface Hierarchy (Tonal Nesting) ──────────────────────────
val Background         = Color(0xFFF7FAFC)
val Surface            = Color(0xFFF7FAFC)
val SurfaceBright      = Color(0xFFF7FAFC)
val SurfaceDim         = Color(0xFFCCDDE4)
val SurfaceContainerLowest  = Color(0xFFFFFFFF)
val SurfaceContainerLow     = Color(0xFFEFF4F7)
val SurfaceContainer        = Color(0xFFE7EFF3)
val SurfaceContainerHigh    = Color(0xFFDFEAEF)
val SurfaceContainerHighest = Color(0xFFD7E5EB)
val SurfaceVariant          = Color(0xFFD7E5EB)
val SurfaceTint             = Color(0xFF555F71)

// ── Primary ────────────────────────────────────────────────────
val Primary            = Color(0xFF555F71)
val PrimaryDim         = Color(0xFF495365)
val PrimaryContainer   = Color(0xFFD9E3F9)
val PrimaryFixed        = Color(0xFFD9E3F9)
val PrimaryFixedDim     = Color(0xFFCBD5EB)
val OnPrimary          = Color(0xFFF6F7FF)
val OnPrimaryContainer = Color(0xFF485264)
val OnPrimaryFixed      = Color(0xFF364051)
val OnPrimaryFixedVariant = Color(0xFF525C6E)
val InversePrimary      = Color(0xFFDEE8FF)

// ── Secondary ──────────────────────────────────────────────────
val Secondary          = Color(0xFF516075)
val SecondaryDim       = Color(0xFF455468)
val SecondaryContainer = Color(0xFFD4E4FC)
val SecondaryFixed      = Color(0xFFD4E4FC)
val SecondaryFixedDim   = Color(0xFFC6D5EE)
val OnSecondary        = Color(0xFFF7F9FF)
val OnSecondaryContainer = Color(0xFF445367)
val OnSecondaryFixed     = Color(0xFF324054)
val OnSecondaryFixedVariant = Color(0xFF4E5D71)

// ── Tertiary ───────────────────────────────────────────────────
val Tertiary           = Color(0xFF5D5D78)
val TertiaryDim        = Color(0xFF50516C)
val TertiaryContainer  = Color(0xFFDBDAFB)
val TertiaryFixed       = Color(0xFFDBDAFB)
val TertiaryFixedDim    = Color(0xFFCDCCEC)
val OnTertiary         = Color(0xFFFBF7FF)
val OnTertiaryContainer = Color(0xFF4C4C67)
val OnTertiaryFixed      = Color(0xFF393953)
val OnTertiaryFixedVariant = Color(0xFF555571)

// ── Error ──────────────────────────────────────────────────────
val Error              = Color(0xFF9F403D)
val ErrorDim           = Color(0xFF4E0309)
val ErrorContainer     = Color(0xFFFE8983)
val OnError            = Color(0xFFFFF7F6)
val OnErrorContainer   = Color(0xFF752121)

// ── Text / On-* ────────────────────────────────────────────────
val OnSurface          = Color(0xFF283439)
val OnSurfaceVariant   = Color(0xFF546166)
val OnBackground       = Color(0xFF283439)
val InverseSurface     = Color(0xFF0B0F10)
val InverseOnSurface   = Color(0xFF9A9D9F)

// ── Outline ────────────────────────────────────────────────────
val Outline            = Color(0xFF707D82)
val OutlineVariant     = Color(0xFFA7B4BA)
// Ghost Border: OutlineVariant at 15% opacity
val GhostBorder        = Color(0x26A7B4BA)

// ── Signature Gradient ─────────────────────────────────────────
val SignatureGradient = Brush.linearGradient(
    colors = listOf(Primary, PrimaryDim)
)

// ── Status Helpers (muted, per design system) ──────────────────
val StatusActive       = Color(0xFFE8F5E9)
val OnStatusActive     = Color(0xFF2E7D32)
val StatusPending      = Color(0xFFFFF3E0)
val OnStatusPending    = Color(0xFFE65100)
val StatusInfo         = Color(0xFFE3F2FD)
val OnStatusInfo       = Color(0xFF1565C0)
