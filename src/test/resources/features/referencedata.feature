Feature: Aggregated view of Instrument reference data

  Scenario: Instrument comes from LME only
    Given the "LME" instrument "PB_03_2018" with these details:
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              |
      | 15-03-2018        | 17-03-2018    | PB     | Lead 13 March 2018 |
    When "LME" publishes instrument "PB_03_2018"
    Then the application publishes the following instrument internally
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
      | 15-03-2018        | 17-03-2018    | PB     | Lead 13 March 2018 | TRUE     |

  Scenario: The same instrument comes from LME followed by PRIME
    Given the "LME" instrument "PB_03_2018" with these details:
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | LME_CODE   |
      | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | PB_03_2018 |
    And a  "PRIME" instrument "PRIME_PB_03_2018" with these details:
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | EXCHANGE_CODE | TRADABLE |
      | 14-03-2018        | 18-03-2018    | LME_PB | Lead 13 March 2018 | PB_03_2018    | FALSE    |
    When "LME" publishes instrument "PB_03_2018"
    Then the application publishes the following instrument internally
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
      | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | TRUE     |
    When "PRIME" publishes instrument "PRIME_PB_03_2018"
    Then the application publishes the following instrument internally
      | LAST_TRADING_DATE | DELIVERY_DATE | MARKET | LABEL              | TRADABLE |
      | 15-03-2018        | 17-03-2018    | LME_PB | Lead 13 March 2018 | FALSE    |

  