CREATE TABLE IF NOT EXISTS payments (
  id SERIAL PRIMARY KEY,
  order_id BIGINT,
  amount NUMERIC(12,2),
  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS outbox (
  id SERIAL PRIMARY KEY,
  aggregate_type VARCHAR(100),
  aggregate_id BIGINT,
  type VARCHAR(100),
  payload JSONB,
  created_at TIMESTAMP DEFAULT now(),
  processed BOOLEAN DEFAULT FALSE
);
