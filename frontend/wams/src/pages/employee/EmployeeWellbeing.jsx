import React, { useEffect, useMemo, useRef, useState } from "react";
import {
    FaCloudRain,
    FaTree,
    FaWater,
    FaPlay,
    FaPause,
    FaSearch,
    FaLeaf,
    FaExternalLinkAlt,
} from "react-icons/fa";
import { FiHeart, FiUsers, FiActivity } from "react-icons/fi";
import { MdFitnessCenter, MdSelfImprovement } from "react-icons/md";

/* -------------------- THEMES -------------------- */
const THEMES = {
    Rain: {
        key: "Rain",
        title: "Rainy Calm",
        blurb: "Relax to steady raindrops.",
        instruction: "Inhale 4s • Hold 2s • Exhale 4s",
        accent: "#3B82F6",
        bg: "linear-gradient(135deg,#dbeafe,#eff6ff)",
        icon: <FaCloudRain size={20} />,
    },
    Forest: {
        key: "Forest",
        title: "Forest Serenity",
        blurb: "Feel grounded with nature’s stillness.",
        instruction: "Slow, steady nose breathing",
        accent: "#16A34A",
        bg: "linear-gradient(135deg,#dcfce7,#f0fdf4)",
        icon: <FaTree size={20} />,
    },
    Ocean: {
        key: "Ocean",
        title: "Ocean Waves",
        blurb: "Let each wave carry stress away.",
        instruction: "Inhale with the swell • Exhale with the fade",
        accent: "#0891B2",
        bg: "linear-gradient(135deg,#cffafe,#ecfeff)",
        icon: <FaWater size={20} />,
    },
};

/* -------------------- RESOURCES -------------------- */
const RESOURCES = [
    {
        name: "BetterHelp – Therapy & Mental Health",
        provider: "BetterHelp",
        category: "Mental Health",
        link: "https://www.betterhelp.com/",
        icon: FiHeart,
    },
    {
        name: "Headspace – Meditation & Mindfulness",
        provider: "Headspace",
        category: "Mental Health",
        link: "https://www.headspace.com/",
        icon: MdSelfImprovement,
    },
    {
        name: "YogaWorks – Workplace Yoga",
        provider: "YogaWorks",
        category: "Physical Fitness",
        link: "https://www.yogaworks.com/",
        icon: MdFitnessCenter,
    },
    {
        name: "Gympass – Corporate Fitness",
        provider: "Gympass",
        category: "Physical Fitness",
        link: "https://www.gympass.com/",
        icon: FiActivity,
    },
    {
        name: "Whole Foods – Nutrition & Wellness",
        provider: "Whole Foods",
        category: "Nutrition",
        link: "https://www.wholefoodsmarket.com/healthy-eating",
        icon: FaLeaf,
    },
    {
        name: "CoachHub – Work‑life Balance Coaching",
        provider: "CoachHub",
        category: "Work-life Balance",
        link: "https://www.coachhub.com/",
        icon: FiUsers,
    },
];

export default function EmployeeWellbeing() {
    /* UI state */
    const [theme, setTheme] = useState("Rain");
    const [running, setRunning] = useState(false);
    const [seconds, setSeconds] = useState(300); // 5:00
    const [query, setQuery] = useState("");
    const [cat, setCat] = useState("All");

    const t = THEMES[theme];

    /* Canvas animation refs */
    const canvasRef = useRef(null);
    const animRef = useRef(0);

    /* --------------- Timer --------------- */
    useEffect(() => {
        if (!running || seconds <= 0) return;
        const id = setInterval(() => setSeconds((s) => s - 1), 1000);
        return () => clearInterval(id);
    }, [running, seconds]);

    const formatTime = (s) => `${Math.floor(s / 60)}:${String(s % 60).padStart(2, "0")}`;

    /* --------------- Categories & Filtering --------------- */
    const categories = useMemo(
        () => ["All", ...new Set(RESOURCES.map((r) => r.category))],
        []
    );

    const filtered = useMemo(() => {
        const q = query.trim().toLowerCase();
        return RESOURCES.filter(
            (r) =>
                (cat === "All" || r.category === cat) &&
                (!q ||
                    r.name.toLowerCase().includes(q) ||
                    r.provider.toLowerCase().includes(q))
        );
    }, [cat, query]);

    /* --------------- Animated Hero (canvas) --------------- */
    useEffect(() => {
        const canvas = canvasRef.current;
        if (!canvas) return;
        const ctx = canvas.getContext("2d");

        const resize = () => {
            canvas.width = window.innerWidth;
            canvas.height = 420; // hero height for the animated stage
        };
        resize();
        window.addEventListener("resize", resize);

        let particles = [];
        if (theme === "Rain") {
            particles = Array.from({ length: 90 }, () => ({
                x: Math.random() * canvas.width,
                y: Math.random() * canvas.height,
                len: 12 + Math.random() * 18,
                spd: 2.8 + Math.random() * 2.2,
            }));
        } else if (theme === "Forest") {
            particles = Array.from({ length: 36 }, () => ({
                x: Math.random() * canvas.width,
                y: Math.random() * canvas.height,
                r: 6 + Math.random() * 8,
                spd: 0.5 + Math.random() * 0.8,
            }));
        }

        const draw = () => {
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            if (theme === "Rain") {
                ctx.strokeStyle = "rgba(59,130,246,0.6)";
                ctx.lineWidth = 1.4;
                ctx.lineCap = "round";
                particles.forEach((p) => {
                    ctx.beginPath();
                    ctx.moveTo(p.x, p.y);
                    ctx.lineTo(p.x, p.y + p.len);
                    ctx.stroke();
                    p.y += p.spd;
                    if (p.y > canvas.height) {
                        p.y = -p.len;
                        p.x = Math.random() * canvas.width;
                    }
                });
            } else if (theme === "Forest") {
                ctx.fillStyle = "rgba(22,163,74,0.18)";
                particles.forEach((p) => {
                    ctx.beginPath();
                    ctx.arc(p.x + Math.sin(p.y / 40) * 8, p.y, p.r, 0, Math.PI * 2);
                    ctx.fill();
                    p.y += p.spd;
                    if (p.y > canvas.height + p.r) {
                        p.y = -p.r;
                        p.x = Math.random() * canvas.width;
                    }
                });
            } else if (theme === "Ocean") {
                ctx.fillStyle = "rgba(8,145,178,0.16)";
                const band = 44;
                for (let y = 0; y < canvas.height; y += band) {
                    ctx.beginPath();
                    for (let x = 0; x <= canvas.width; x += 12) {
                        ctx.lineTo(x, y + Math.sin(x / 110 + y / 50 + Date.now() / 900) * 18);
                    }
                    ctx.lineTo(canvas.width, canvas.height);
                    ctx.lineTo(0, canvas.height);
                    ctx.closePath();
                    ctx.fill();
                }
            }

            animRef.current = requestAnimationFrame(draw);
        };
        draw();

        return () => {
            cancelAnimationFrame(animRef.current);
            window.removeEventListener("resize", resize);
        };
    }, [theme]);

    /* -------------------- UI -------------------- */
    return (
        <div className="wb" style={{ background: t.bg }}>
            <canvas ref={canvasRef} className="wb-hero" />

            {/* Header */}
            <header className="wb-head">
                <h1>Employee Wellbeing</h1>
                <p className="wb-sub">{t.blurb}</p>
            </header>

            {/* Theme Switch */}
            <div className="wb-themes">
                {Object.values(THEMES).map((x) => (
                    <button
                        key={x.key}
                        onClick={() => setTheme(x.key)}
                        className={`wb-chip ${theme === x.key ? "active" : ""}`}
                        style={theme === x.key ? { background: x.accent } : {}}
                        aria-pressed={theme === x.key}
                    >
                        {x.icon} {x.key}
                    </button>
                ))}
            </div>

            {/* Breathing Panel */}
            <section className="wb-breath">
                <div className="wb-bubble-wrap">
                    <span className={`bubble ${running ? "anim" : ""}`} />
                    <span className="ring r1" />
                    <span className="ring r2" />
                    <span className="ring r3" />
                </div>

                <h2 style={{ color: t.accent }}>{t.title}</h2>
                <p className="wb-instruction">{t.instruction}</p>

                <div className="wb-time">{formatTime(seconds)}</div>

                <div className="wb-cta">
                    <button
                        className="wb-btn"
                        style={{ background: t.accent }}
                        onClick={() => setRunning((v) => !v)}
                    >
                        {running ? <FaPause /> : <FaPlay />} {running ? "Pause" : "Start"}
                    </button>
                </div>
            </section>

            {/* Resources */}
            <section className="resources">
                <h2>Helpful Resources</h2>

                <div className="filters">
                    <div className="search">
                        <FaSearch />
                        <input
                            placeholder="Search resources..."
                            value={query}
                            onChange={(e) => setQuery(e.target.value)}
                        />
                    </div>
                    <div className="categories">
                        {categories.map((c) => (
                            <button
                                key={c}
                                onClick={() => setCat(c)}
                                className={`pill ${cat === c ? "active" : ""}`}
                                style={cat === c ? { background: t.accent } : {}}
                            >
                                {c}
                            </button>
                        ))}
                    </div>
                </div>

                <div className="grid">
                    {filtered.map((r) => {
                        const Icon = r.icon;
                        return (
                            <article className="card" key={r.name}>
                                <div className="ico">
                                    <Icon size={28} style={{ color: t.accent }} />
                                </div>
                                <h3>{r.name}</h3>
                                <p>{r.provider}</p>
                                <a
                                    href={r.link}
                                    target="_blank"
                                    rel="noreferrer"
                                    className="cta"
                                    style={{ background: t.accent }}
                                >
                                    Learn More <FaExternalLinkAlt size={12} />
                                </a>
                            </article>
                        );
                    })}
                </div>
            </section>

            {/* Footer */}
            <footer className="wb-foot">© 2025 WAMS – Employee Wellbeing</footer>

            {/* Styles */}
            <style>{CSS}</style>
        </div>
    );
}

/* -------------------- CSS -------------------- */
const CSS = `
/* Root container */
.wb{
  min-height:100vh;
  overflow-x:hidden;
  position:relative;
  font-family:'Poppins','Segoe UI',system-ui,-apple-system,Roboto,Arial,sans-serif;
  color:#0f2a45;
}

/* Animated hero canvas */
.wb-hero{
  position:absolute; top:0; left:0; right:0;
  height:420px;
  width:100%;
  pointer-events:none;
  z-index:0;
}

/* Header */
.wb-head{
  position:relative; z-index:2;
  padding:28px 16px 6px;
  text-align:center;
}
.wb-head h1{
  margin:0;
  font-weight:800;
  font-size:2.2rem;
  letter-spacing:.2px;
}
.wb-sub{
  margin:6px 0 0;
  color:#47607a;
  font-weight:600;
}

/* Theme chips */
.wb-themes{
  position:relative; z-index:2;
  display:flex; gap:10px; justify-content:center; align-items:center;
  padding:10px 16px 0;
}
.wb-chip{
  border:none; border-radius:999px;
  padding:10px 16px; font-weight:700; font-size:.95rem;
  background:#ffffffc7; color:#20364b; backdrop-filter:blur(10px);
  box-shadow:0 6px 18px rgba(24,52,82,.08);
  transition:transform .18s ease, box-shadow .18s ease, background .18s ease, color .18s ease;
  cursor:pointer;
  display:inline-flex; align-items:center; gap:8px;
}
.wb-chip:hover{ transform:translateY(-1px); box-shadow:0 10px 22px rgba(24,52,82,.12); }
.wb-chip.active{ color:#fff; box-shadow:0 12px 26px rgba(24,52,82,.16); }

/* Breathing section */
.wb-breath{
  position:relative; z-index:2;
  max-width:950px; margin: 14px auto 8px;
  padding:22px 18px 8px;
  border-radius:20px;
  background: radial-gradient(1100px 380px at 50% -80px, rgba(255,255,255,.9), rgba(255,255,255,.55) 60%, rgba(255,255,255,.34) 100%);
  border:1px solid rgba(221,231,244,.72);
  box-shadow:0 18px 48px rgba(20,44,71,.08);
  text-align:center;
}
.wb-breath h2{
  margin:8px 0 4px;
  font-size:1.5rem; font-weight:800;
}
.wb-instruction{
  margin:0 0 8px;
  color:#5a6f86; font-weight:600;
}

/* Bubble + rings */
.wb-bubble-wrap{
  position:relative; width:180px; height:180px; margin:12px auto 10px;
}
.bubble{
  position:absolute; inset:0;
  margin:auto; width:160px; height:160px; border-radius:50%;
  background:#fff;
  box-shadow:0 10px 22px rgba(0,0,0,.08), inset 0 0 0 2px rgba(15,61,118,.06);
}
.bubble.anim{ animation:breath 7s ease-in-out infinite; }

.ring{ position:absolute; inset:0; margin:auto; width:180px; height:180px; border-radius:50%; border:2px solid rgba(15,61,118,.08); }
.r1{ animation:ring 7s ease-in-out infinite; }
.r2{ animation:ring 7s ease-in-out 1s infinite; }
.r3{ animation:ring 7s ease-in-out 2s infinite; }

@keyframes breath{
  0%,100%{ transform:scale(.86); }
  50%{ transform:scale(1.24); }
}
@keyframes ring{
  0%{ transform:scale(.9); opacity:.45; }
  60%{ transform:scale(1.25); opacity:.12; }
  100%{ transform:scale(1.35); opacity:0; }
}

.wb-time{
  font-size:1.2rem; font-weight:800; letter-spacing:.3px; margin:6px 0 10px;
}
.wb-cta{ margin-bottom:8px; }
.wb-btn{
  border:none; color:#fff; border-radius:12px; cursor:pointer;
  padding:10px 16px; font-weight:800;
  box-shadow:0 12px 26px rgba(24,52,82,.16);
  display:inline-flex; align-items:center; gap:8px;
}

/* Resources section */
.resources{
  position:relative; z-index:2;
  max-width:1200px; margin: 18px auto 30px;
  padding:10px 16px 40px;
  text-align:center;
}
.resources h2{
  margin-bottom:16px;
  font-size:1.7rem; font-weight:800; color:#0f2a45;
}

/* Filters */
.filters{
  display:flex; flex-wrap:wrap; gap:10px; justify-content:center; align-items:center;
  margin-bottom:22px;
}
.search{
  display:flex; align-items:center; gap:8px;
  background:#ffffffee;
  border:1px solid #dbe6f3; border-radius:30px; padding:8px 16px;
  box-shadow:0 6px 18px rgba(15,61,118,.06);
}
.search input{
  border:none; outline:none; background:transparent; font-size:15px; min-width:220px;
}
.categories .pill{
  border:none; padding:8px 14px; border-radius:30px;
  background:#eef4fa; color:#1a2c3d; font-weight:800; cursor:pointer;
  transition: all .2s ease; box-shadow:0 6px 16px rgba(24,52,82,.06);
}
.categories .pill:hover{ transform:translateY(-1px); }
.categories .pill.active{
  color:#fff; box-shadow:0 8px 18px rgba(24,52,82,.15);
}

/* Grid + cards */
.grid{
  display:grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap:20px; align-items:stretch;
}
.card{
  background: linear-gradient(145deg,#ffffffb8,#f3f6fa);
  backdrop-filter: blur(10px);
  border-radius:18px; border:1px solid #dde6f4;
  padding:20px; text-align:center;
  box-shadow:0 8px 28px rgba(20,44,71,.08);
  transition: all .2s ease;
  display:flex; flex-direction:column; justify-content:flex-start; align-items:center;
}
.card:hover{ transform: translateY(-4px) scale(1.02); box-shadow:0 12px 32px rgba(20,44,71,.12); }
.card .ico{ display:grid; place-items:center; width:46px; height:46px; border-radius:12px; background:#fff; border:1px solid #e7eef9; }
.card h3{ margin:12px 0 6px; font-size:1.08rem; font-weight:800; color:#10253b; }
.card p{ margin:0 0 12px; color:#6a7e93; font-weight:600; }
.card .cta{
  display:inline-flex; align-items:center; gap:6px; color:#fff;
  padding:8px 14px; border-radius:12px; font-weight:800; text-decoration:none;
  transition: transform .2s ease;
}
.card .cta:hover{ transform: translateY(-2px); }

/* Footer */
.wb-foot{
  text-align:center; color:#fff; background:#0f2a45; padding:14px; font-weight:700;
}

/* Responsive tweaks */
@media (max-width: 640px){
  .wb-head h1{ font-size:1.8rem; }
  .wb-breath{ padding-inline:12px; }
  .wb-bubble-wrap{ width:160px; height:160px; }
  .bubble{ width:140px; height:140px; }
  .search input{ min-width:166px; }
}
`;
