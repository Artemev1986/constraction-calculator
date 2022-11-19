CREATE TABLE IF NOT EXISTS users (
    chat_id BIGINT PRIMARY KEY NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    user_name VARCHAR(100),
    registered_on TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT uniq_users_email UNIQUE (user_name)
);

CREATE TABLE IF NOT EXISTS questionnaires (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    customer_id BIGINT REFERENCES users(chat_id) NOT NULL,
    slab_perimeter REAL NOT NULL,
    slab_area_full REAL NOT NULL,
    slab_area_base REAL NOT NULL,
    slab_area_heated REAL NOT NULL,
    number_of_axes REAL NOT NULL,
    route_length REAL NOT NULL,
    sewer_risers REAL NOT NULL,
    draw_off_points REAL NOT NULL,
    cable_entry REAL NOT NULL,
    ground REAL NOT NULL,
    length_outside_wall REAL NOT NULL,
    length_inside_wall REAL NOT NULL,
    plate_rib_width REAL NOT NULL,
    rib_volume REAL NOT NULL,
    pit_area REAL NOT NULL,
    pit_depth REAL NOT NULL,
    blind_area REAL NOT NULL,
    insulation_thickness REAL NOT NULL,
    sand_bed_thickness REAL NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE
);