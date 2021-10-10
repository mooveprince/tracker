DROP TABLE stock_tracker IF EXISTS;

CREATE TABLE stock_tracker  (
    quarter TINYINT,
    stock VARCHAR(8),
    date DATE,
    open VARCHAR(8),
    high VARCHAR(8),
    low VARCHAR(8),
    close VARCHAR(8),
    volume BIGINT,
    percent_change_price REAL,
    percent_change_volume_over_last_wk DOUBLE,
    previous_weeks_volume BIGINT,
    next_weeks_open VARCHAR(8),
    next_weeks_close VARCHAR(8),
    percent_change_next_weeks_price REAL,
    days_to_next_dividend SMALLINT,
    percent_return_next_dividend REAL,
    PRIMARY KEY (quarter, stock, date)
);