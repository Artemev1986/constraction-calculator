CREATE TABLE IF NOT EXISTS users (
    chat_id BIGINT PRIMARY KEY NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    user_name VARCHAR(100),
    registered_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS questionnaires (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    chat_id BIGINT REFERENCES users(chat_id) NOT NULL,
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
    length_inside_wall REAL NOT NULL,
    plate_rib_width REAL NOT NULL,
    pit_depth REAL NOT NULL,
    insulation_thickness REAL NOT NULL,
    sand_bed_thickness REAL NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS main_price (
    id BIGINT PRIMARY KEY NOT NULL,
    breakdown_of_axes REAL NOT NULL,
    development_of_pit REAL NOT NULL,
    equipping_pillow REAL NOT NULL,
    laying_waterproof REAL NOT NULL,
    laying_geotextile REAL NOT NULL,
    insulation_first_layer REAL NOT NULL,
    insulation_second_layer REAL NOT NULL,
    installation_plate REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS communication_price (
    id BIGINT PRIMARY KEY NOT NULL,
    installation_heat_floor_pipes REAL NOT NULL,
    installation_sewer_pipes REAL NOT NULL,
    installation_source_water_pipes REAL NOT NULL,
    installation_heating_pipes REAL NOT NULL,
    cable_laying REAL NOT NULL,
    installation_backup_embeds REAL NOT NULL,
    installation_water_embeds REAL NOT NULL,
    installation_ground REAL NOT NULL,
    manifold_installation REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS concrete_price (
    id BIGINT PRIMARY KEY NOT NULL,
    formwork_assembly_disassembly REAL NOT NULL,
    installation_formwork_steps REAL NOT NULL,
    rib_reinforcement REAL NOT NULL,
    slab_reinforcement REAL NOT NULL,
    concreting REAL NOT NULL,
    poles_for_terrace REAL NOT NULL,
    concrete_grinding REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS additional_price (
    id BIGINT PRIMARY KEY NOT NULL,
    installation_insulated_blind_area REAL NOT NULL,
    installation_drainage_ditch REAL NOT NULL,
    installation_drainage_wells REAL NOT NULL,
    installation_rain_inlets REAL NOT NULL,
    installation_storm_sewer_pipes REAL NOT NULL,
    installation_septic REAL NOT NULL,
    installation_caisson REAL NOT NULL,
    business_trip REAL NOT NULL,
    transport REAL NOT NULL,
    backhoe_loader REAL NOT NULL,
    concrete_pump REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS full_price (
    id BIGINT PRIMARY KEY NOT NULL,
    main_price_id BIGINT REFERENCES main_price(id) NOT NULL,
    communication_price_id BIGINT REFERENCES communication_price(id) NOT NULL,
    concrete_price_id BIGINT REFERENCES concrete_price(id) NOT NULL,
    additional_price_id BIGINT REFERENCES additional_price(id) NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE
);