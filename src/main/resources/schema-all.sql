DROP TABLE stock_tracker IF EXISTS;

CREATE TABLE stock_tracker  (
    quarter INT,
    stock VARCHAR(10),
    date VARCHAR(10),
    open VARCHAR(20),
    high VARCHAR(20),
    low VARCHAR(20),
    close VARCHAR(20),
    volume VARCHAR(20),
    percent_change_price VARCHAR(20),
    percent_change_volume_over_last_wk VARCHAR(20),
    previous_weeks_volume VARCHAR(20),
    next_weeks_open VARCHAR(20),
    next_weeks_close VARCHAR(20),
    percent_change_next_weeks_price VARCHAR(20),
    days_to_next_dividend VARCHAR(20),
    percent_return_next_dividend VARCHAR(20),
    PRIMARY KEY (quarter, stock, date)
);