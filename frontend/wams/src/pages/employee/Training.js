// src/Pages/Training.js
import React, { useMemo, useState } from 'react';

// Font Awesome
import {
    FaCertificate,
    FaCrown,
    FaChalkboardTeacher,
    FaUniversity,
    FaLinkedin,
    FaShieldAlt,
    FaExternalLinkAlt,
    FaAws,
    FaMicrosoft,
    FaGoogle,
    FaGraduationCap,
    FaHome,
    FaUserCog
} from 'react-icons/fa';

/* =========================
   DATA
   ========================= */
const trainingsData = [
    { name: "PMP Certification (PMI)", link: "https://www.pmi.org/certifications/project-management-pmp", category: "Project Management", provider: "PMI" },
    { name: "PRINCE2 Foundation & Practitioner", link: "https://www.axelos.com/certifications/prince2", category: "Project Management", provider: "AXELOS" },
    { name: "Certified ScrumMaster (CSM)", link: "https://www.scrumalliance.org/get-certified/scrum-master-track/certified-scrummaster", category: "Project Management", provider: "Scrum Alliance" },

    { name: "AWS Certified Solutions Architect – Associate", link: "https://aws.amazon.com/certification/certified-solutions-architect-associate/", category: "Technical / IT", provider: "AWS" },
    { name: "Microsoft Certified: Azure Fundamentals", link: "https://learn.microsoft.com/en-us/certifications/azure-fundamentals/", category: "Technical / IT", provider: "Microsoft" },
    { name: "Google Cloud Associate Cloud Engineer", link: "https://cloud.google.com/certification/cloud-engineer", category: "Technical / IT", provider: "Google Cloud" },
    { name: "CompTIA Security+", link: "https://www.comptia.org/certifications/security", category: "Technical / IT", provider: "CompTIA" },

    { name: "LinkedIn Learning – Leadership Development", link: "https://www.linkedin.com/learning/topics/leadership", category: "Employee Development", provider: "LinkedIn Learning" },
    { name: "Coursera – Workplace Well‑being", link: "https://www.coursera.org/learn/workplace-wellbeing", category: "Employee Development", provider: "Coursera" },
    { name: "Harvard Online – Negotiation Mastery", link: "https://online.hbs.edu/courses/negotiation/", category: "Employee Development", provider: "Harvard Business School Online" }
];

/* =========================
   ICON MAP
   ========================= */
const providerIconMap = {
    AWS: FaAws,
    Microsoft: FaMicrosoft,
    "Google Cloud": FaGoogle,
    "LinkedIn Learning": FaLinkedin,
    Coursera: FaGraduationCap,
    PMI: FaCertificate,
    AXELOS: FaCrown,
    "Scrum Alliance": FaChalkboardTeacher,
    CompTIA: FaShieldAlt,
    "Harvard Business School Online": FaUniversity
};

const brandColorMap = {
    AWS: "#FF9900",
    Microsoft: "#0078D4",
    "Google Cloud": "#4285F4",
    "LinkedIn Learning": "#0A66C2",
    Coursera: "#2A73CC",
    PMI: "#0052CC",
    AXELOS: "#7A003C",
    "Scrum Alliance": "#F16522",
    CompTIA: "#E31C3D",
    "Harvard Business School Online": "#A41034"
};

function hexToRgba(hex, alpha = 0.12) {
    const h = hex.replace("#", "");
    if (!(h.length === 6 || h.length === 3)) return `rgba(15,61,118,${alpha})`;
    const toDec = (str) => parseInt(str.length === 1 ? str + str : str, 16);
    const r = toDec(h.substring(0, h.length === 3 ? 1 : 2));
    const g = toDec(h.substring(h.length === 3 ? 1 : 2, h.length === 3 ? 2 : 4));
    const b = toDec(h.substring(h.length === 3 ? 2 : 4, h.length === 3 ? 3 : 6));
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}

function getIconForProvider(provider) {
    const IconComp = providerIconMap[provider] || FaCertificate;
    const color = brandColorMap[provider] || "#0f3d76";
    const bg = hexToRgba(color, 0.12);
    return { IconComp, color, bg };
}

/* =========================
   STYLES
   ========================= */
const styles = {
    /** Breaks out of any parent container so the page covers the whole screen */
    fullBleed: {
        position: 'relative',
        left: '50%',
        right: '50%',
        marginLeft: '-50vw',
        marginRight: '-50vw',
        width: '100vw',
        minHeight: '100vh',
        background: '#f7f9fc',
        overflowX: 'hidden' // avoid tiny horizontal scrollbars on some browsers
    },

    nav: {
        width: "100%",
        background: "#0f3d76",
        color: "#fff",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: "12px 24px",
        fontSize: 16,
        fontWeight: 500
    },
    navLeft: { display: "flex", alignItems: "center", gap: 10 },
    navRight: { display: "flex", alignItems: "center", gap: 20 },

    /** Centered content wrapper with max width so text/cards don't stretch too far */
    section: {
        width: "100%",
        maxWidth: 1200,
        margin: "0 auto",
        padding: "0 24px"
    },

    header: {
        paddingTop: 32,
        paddingBottom: 16,
        display: "flex",
        flexDirection: "column",
        gap: 8
    },
    title: { margin: 0, color: "#0f3d76", fontSize: 28, fontWeight: 700 },
    subtitle: { margin: 0, color: "#4b5563" },

    toolbar: {
        display: "flex",
        flexWrap: "wrap",
        gap: 12,
        alignItems: "center",
        marginBottom: 16
    },
    search: {
        flex: "1 1 260px",
        minWidth: 220,
        padding: "10px 12px",
        border: "1px solid #d1d5db",
        borderRadius: 10,
        outline: "none",
        background: "#fff",
        fontSize: 14
    },
    chipsRow: { display: "flex", flexWrap: "wrap", gap: 8 },
    chip: (active) => ({
        padding: "8px 12px",
        borderRadius: 999,
        border: `1px solid ${active ? "#0f3d76" : "#cbd5e1"}`,
        background: active ? "#e6f0ff" : "#fff",
        color: active ? "#0f3d76" : "#475569",
        cursor: "pointer",
        fontSize: 13,
        userSelect: "none"
    }),

    meta: { marginTop: 8, marginBottom: 12, color: "#6b7280", fontSize: 13 },

    grid: {
        display: "grid",
        gridTemplateColumns: "repeat(auto-fill, minmax(280px, 1fr))",
        gap: 20,
        paddingBottom: 32
    },
    card: {
        display: "flex",
        flexDirection: "column",
        gap: 10,
        padding: 16,
        background: "#fff",
        border: "1px solid #e5e7eb",
        borderRadius: 16,
        boxShadow: "0 4px 14px rgba(15, 61, 118, 0.06)"
    },
    cardHeader: { display: "flex", gap: 12, alignItems: "center" },
    iconWrap: (bg) => ({
        width: 42,
        height: 42,
        borderRadius: 12,
        display: "grid",
        placeItems: "center",
        background: bg
    }),
    name: { margin: 0, fontSize: 16, fontWeight: 600, color: "#111827" },
    provider: { margin: 0, fontSize: 13, color: "#6b7280" },
    tag: {
        alignSelf: "flex-start",
        padding: "4px 10px",
        fontSize: 12,
        borderRadius: 999,
        background: "#f3f4f6",
        color: "#374151",
        border: "1px solid #e5e7eb"
    },
    actions: { marginTop: 6, display: "flex", gap: 8 },
    linkBtn: {
        display: "inline-flex",
        alignItems: "center",
        gap: 8,
        padding: "10px 12px",
        borderRadius: 10,
        border: "1px solid #0f3d76",
        background: "#0f3d76",
        color: "#fff",
        fontSize: 14,
        textDecoration: "none"
    }
};

/* =========================
   COMPONENT
   ========================= */
const Training = () => {
    const [query, setQuery] = useState("");
    const [activeCategory, setActiveCategory] = useState("All");

    const categories = useMemo(() => {
        const set = new Set(trainingsData.map((t) => t.category));
        return ["All", ...Array.from(set)];
    }, []);

    const filtered = useMemo(() => {
        const q = query.trim().toLowerCase();
        return trainingsData.filter((t) => {
            const inCategory = activeCategory === "All" || t.category === activeCategory;
            const inQuery =
                !q ||
                t.name.toLowerCase().includes(q) ||
                t.provider.toLowerCase().includes(q) ||
                t.category.toLowerCase().includes(q);
            return inCategory && inQuery;
        });
    }, [query, activeCategory]);

    return (
        <div style={styles.fullBleed}>
            {/* Full-width Navigation */}
            <div style={styles.nav}>
                <div style={styles.navLeft}><FaHome /> WAMS</div>
                <div style={styles.navRight}><FaUserCog /> Dashboard</div>
            </div>

            {/* Header (centered content) */}
            <section style={{ ...styles.section, ...styles.header }}>
                <h2 style={styles.title}>Professional Trainings & Certifications</h2>
                <p style={styles.subtitle}>
                    Browse curated certifications and courses. Use the search and category filters to find exactly what you need.
                </p>
            </section>

            {/* Search + Filters */}
            <section style={{ ...styles.section }}>
                <div style={styles.toolbar}>
                    <input
                        type="text"
                        placeholder="Search by course, provider, or keyword…"
                        value={query}
                        onChange={(e) => setQuery(e.target.value)}
                        style={styles.search}
                    />
                    <div style={styles.chipsRow}>
                        {categories.map((cat) => {
                            const active = activeCategory === cat;
                            return (
                                <button
                                    key={cat}
                                    onClick={() => setActiveCategory(cat)}
                                    style={styles.chip(active)}
                                >
                                    {cat}
                                </button>
                            );
                        })}
                    </div>
                </div>
                <div style={styles.meta}>
                    Showing <strong>{filtered.length}</strong> of {trainingsData.length} results
                    {activeCategory !== "All" && <> in <strong>{activeCategory}</strong></>}
                    {query && <> for “<strong>{query}</strong>”</>}
                </div>
            </section>

            {/* Cards Grid */}
            <section style={styles.section}>
                <div style={styles.grid}>
                    {filtered.map((t) => {
                        const { IconComp, color, bg } = getIconForProvider(t.provider);
                        return (
                            <article key={t.name} style={styles.card}>
                                <div style={styles.cardHeader}>
                                    <div style={styles.iconWrap(bg)}>
                                        <IconComp size={22} style={{ color }} />
                                    </div>
                                    <div>
                                        <h3 style={styles.name}>{t.name}</h3>
                                        <p style={styles.provider}>{t.provider}</p>
                                    </div>
                                </div>
                                <span style={styles.tag}>{t.category}</span>
                                <div style={styles.actions}>
                                    <a
                                        href={t.link}
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        style={styles.linkBtn}
                                    >
                                        Visit official page <FaExternalLinkAlt />
                                    </a>
                                </div>
                            </article>
                        );
                    })}
                </div>
            </section>
        </div>
    );
};

export default Training;
