/*CREATE DATABASE periodicalSubscription;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;*/
CREATE TABLE  IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(70) UNIQUE NOT NULL
);

CREATE TABLE  IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL REFERENCES roles,
    avatar TEXT,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

/*DROP TABLE IF EXISTS periodicals;
DROP TABLE IF EXISTS periodical_types;
DROP TABLE IF EXISTS periodical_categories;
DROP TABLE IF EXISTS periodical_statuses;*/
CREATE TABLE  IF NOT EXISTS periodical_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(70) UNIQUE NOT NULL
);

CREATE TABLE  IF NOT EXISTS periodical_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE  IF NOT EXISTS periodical_statuses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE  IF NOT EXISTS periodicals (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    publication_date DATE NOT NULL,
    issues_amount_in_year INT NOT NULL,
    price DECIMAL(6, 2) NOT NULL,
    language VARCHAR(70) NOT NULL,
    image TEXT NOT NULL,
    type_id BIGINT NOT NULL REFERENCES periodical_types,
    category_id BIGINT NOT NULL REFERENCES periodical_categories,
    status_id BIGINT NOT NULL REFERENCES periodical_statuses,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
/*DROP TABLE IF EXISTS subscriptions;
DROP TABLE IF EXISTS subscription_statuses;*/

CREATE TABLE  IF NOT EXISTS subscription_statuses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);
CREATE TABLE  IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users,
    total_cost DECIMAl(8, 2) NOT NULL,
    status_id BIGINT NOT NULL REFERENCES subscription_statuses,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

--DROP TABLE IF EXISTS subscription_details;
CREATE TABLE  IF NOT EXISTS subscription_details (
    id BIGSERIAL PRIMARY KEY,
    subscription_id BIGINT NOT NULL REFERENCES subscriptions,
    periodical_id BIGINT NOT NULL REFERENCES periodicals,
    subscription_duration_in_years INT NOT NULL,
    periodical_current_price DECIMAl(6, 2) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
/*DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS payment_methods;*/
CREATE TABLE  IF NOT EXISTS payment_methods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
CREATE TABLE  IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users,
    subscription_id BIGINT NOT NULL REFERENCES subscriptions,
    payment_time TIMESTAMP NOT NULL,
    payment_method_id BIGINT NOT NULL REFERENCES payment_methods,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);










