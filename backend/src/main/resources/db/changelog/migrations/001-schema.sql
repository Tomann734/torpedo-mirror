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
    dtype VARCHAR(50) NOT NULL,
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


