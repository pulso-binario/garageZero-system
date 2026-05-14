-- 1. Roles table.
CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR2(50) UNIQUE NOT NULL
);

-- 2. User-Roles Junction Table (Many-to-many)
CREATE TABLE user_roles
(
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id INT  NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- 3. Social Accounts Table (For Google/Apple Auth)
CREATE TABLE social_accounts(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    provider VARCHAR2(50) NOT NULL, -- e.g., 'google', 'apple'.
    provider_id VARCHAR2(255) NOT NULL, -- The unique ID returned by Google/Apple.
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (provider, provider_id) -- Ensures a specific Google account can only be linked once.
);