-- liquibase formatted sql
-- changeset tomann734:1

CREATE TABLE mirror_user (
    username VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT mirror_user_pk PRIMARY KEY (username)
);

CREATE TABLE module (
    name VARCHAR(255),
    dtype VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT module_pk PRIMARY KEY (name)
);

CREATE TABLE submodule (
    uuid UUID,
    module_name VARCHAR(255) NOT NULL,
    record_time TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT submodule_pk PRIMARY KEY (uuid),
    CONSTRAINT submodule_module_fk FOREIGN KEY (module_name) REFERENCES module(name) ON DELETE CASCADE
);

CREATE TABLE user_module (
    username VARCHAR(255) NOT NULL,
    module_name VARCHAR(255) NOT NULL,
    CONSTRAINT user_module_pk PRIMARY KEY (username, module_name),
    CONSTRAINT user_module_user_fk FOREIGN KEY (username) REFERENCES mirror_user(username) ON DELETE CASCADE,
    CONSTRAINT user_module_module_fk FOREIGN KEY (module_name) REFERENCES module(name) ON DELETE CASCADE
);

CREATE TABLE football_module (
    uuid UUID,
    team_id INT4 NOT NULL,
    home_team VARCHAR(100) NOT NULL,
    away_team VARCHAR(100) NOT NULL,
    stadium_name VARCHAR(100) NOT NULL,
    competition VARCHAR(100) NOT NULL,
    kickoff_time TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT football_module_pk PRIMARY KEY (uuid),
    CONSTRAINT football_module_submodule_fk FOREIGN KEY (uuid) REFERENCES submodule(uuid) ON DELETE CASCADE
);

CREATE TABLE weather_module (
    uuid UUID,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    is_day BOOLEAN NOT NULL,
    current_temperature DOUBLE PRECISION NOT NULL,
    current_rain DOUBLE PRECISION NOT NULL,
    current_shower DOUBLE PRECISION NOT NULL,
    current_snow DOUBLE PRECISION NOT NULL,
    current_cloud_cover INT NOT NULL,
    min_temperature DOUBLE PRECISION NOT NULL,
    max_temperature DOUBLE PRECISION NOT NULL,
    min_rain DOUBLE PRECISION NOT NULL,
    max_rain DOUBLE PRECISION NOT NULL,
    min_shower DOUBLE PRECISION NOT NULL,
    max_shower DOUBLE PRECISION NOT NULL,
    min_snow DOUBLE PRECISION NOT NULL,
    max_snow DOUBLE PRECISION NOT NULL,
    min_cloud_coverage INT NOT NULL,
    max_cloud_coverage INT NOT NULL,
    CONSTRAINT weather_module_pk PRIMARY KEY (uuid),
    CONSTRAINT weather_module_submodule_fk FOREIGN KEY (uuid) REFERENCES submodule(uuid) ON DELETE CASCADE
);

CREATE TABLE google_calendar_module(
    uuid UUID,
    calendar_id VARCHAR(100) NOT NULL,
    summary VARCHAR(100) NOT NULL,
    description TEXT,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT google_calendar_module_pk PRIMARY KEY (uuid),
    CONSTRAINT google_calendar_module_submodule_fk FOREIGN KEY (uuid) REFERENCES submodule(uuid) ON DELETE CASCADE
);

CREATE TABLE fitbit_auth(
    user_id VARCHAR(32),
    access_token VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    CONSTRAINT fitbit_auth_pk PRIMARY KEY (user_id)
);

CREATE TABLE fitbit_module (
    uuid UUID,
    user_id VARCHAR(32) NOT NULL,
    heart_rmssd_day DOUBLE PRECISION,
    heart_rmssd_deep DOUBLE PRECISION,
    heart_resting_rate INT4,
    breathing_rate DOUBLE PRECISION,
    sleep_minutes_in_bed INT4,
    sleep_minutes_asleep INT4,
    sleep_efficiency INT4,
    sleep_start_time TIMESTAMP WITH TIME ZONE,
    sleep_end_time TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fitbit_module_pk PRIMARY KEY (uuid),
    CONSTRAINT fitbit_module_submodule_fk FOREIGN KEY (uuid) REFERENCES submodule(uuid) ON DELETE CASCADE
);

CREATE TABLE nasa_module (
    uuid UUID,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    date DATE NOT NULL,
    url VARCHAR(200) NOT NULL,
    file_name VARCHAR(100) NOT NULL,
    CONSTRAINT nasa_module_pk PRIMARY KEY (uuid),
    CONSTRAINT nasa_module_submodule_fk FOREIGN KEY (uuid) REFERENCES submodule(uuid) ON DELETE CASCADE
);

CREATE TABLE personal_picture_module (
    uuid UUID,
    file_name VARCHAR(100) NOT NULL,
    CONSTRAINT personal_picture_module_pk PRIMARY KEY (uuid),
    CONSTRAINT personal_picture_submodule_fk FOREIGN KEY (uuid) REFERENCES submodule(uuid) ON DELETE CASCADE
);

CREATE TABLE wikimedia_module (
    uuid UUID,
    CONSTRAINT wikimedia_module_pk PRIMARY KEY (uuid),
    CONSTRAINT wikimedia_module_fk FOREIGN KEY (uuid) REFERENCES submodule(uuid) ON DELETE CASCADE
);

CREATE TABLE wikimedia_fact(
    uuid UUID,
    submodule_uuid UUID,
    description TEXT NOT NULL,
    year INT4 NOT NULL,
    CONSTRAINT wikimedia_fact_pk PRIMARY KEY (uuid),
    CONSTRAINT wikimedia_fact_wikimedia_module_fk FOREIGN KEY (submodule_uuid) REFERENCES wikimedia_module(uuid) ON DELETE CASCADE
);



