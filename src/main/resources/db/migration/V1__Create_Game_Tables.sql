-- Create Game Table
CREATE TABLE IF NOT EXISTS game (
    id VARCHAR(36) PRIMARY KEY,
    image LONGTEXT,
    type INT,
    name VARCHAR(255) NOT NULL,
    max_diamonds_allowed INT DEFAULT 0,
    enable_hints BOOLEAN DEFAULT TRUE,
    hint_config JSON,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    INDEX idx_name (name),
    INDEX idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Game Detail Table
CREATE TABLE IF NOT EXISTS game_detail (
    id VARCHAR(36) PRIMARY KEY,
    game_id VARCHAR(36) NOT NULL,
    x DOUBLE,
    y DOUBLE,
    width DOUBLE,
    height DOUBLE,
    d LONGTEXT,
    color VARCHAR(50),
    max_diamonds INT DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE,
    INDEX idx_game_detail_game (game_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Game Hint Table
CREATE TABLE IF NOT EXISTS game_hint (
    id VARCHAR(36) PRIMARY KEY,
    game_detail_id VARCHAR(36) NOT NULL,
    level INT NOT NULL,
    text LONGTEXT,
    audio_url LONGTEXT,
    audio_file_name VARCHAR(255),
    point_deduction INT DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (game_detail_id) REFERENCES game_detail(id) ON DELETE CASCADE,
    UNIQUE KEY unique_detail_level (game_detail_id, level),
    INDEX idx_game_hint_detail (game_detail_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;