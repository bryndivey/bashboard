{:config {:order 0, :description "Bookings", :name :bookings}
 :data
 [
  [:node-create [] :map]
  [:node-create [:main] :map]
  [:node-create [:main :counter] :map]
  [:transform-enable [:main :counter] :inc [{:io.pedestal.app.messages/topic [:counter]}]]
  [:node-create [:main :sites] :map]
  [:transform-enable [:main :sites] :empty [{:io.pedestal.app.messages/topic [:sites "zadev1" :bookings]}]]
  [:node-create [:main :sites "zadev1"] :map]
  [:node-create [:main :sites "zadev2"] :map]
  [:value [:main :sites "zadev2"] nil {:bookings {9324481540 {:id 9324481540, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  [:node-create [:main :sites "zadev3"] :map]
  [:node-create [:main :sites "zadev4"] :map]
  [:node-create [:main :sites-count] :map]
  [:value [:main :sites-count] nil 4]
  [:node-create [:main :free-sites] :map]
  [:value [:main :free-sites] nil ("zadev1" "zadev3" "zadev4")]
  :break
  [:value [:main :sites "zadev4"] nil {:bookings {8051288947 {:id 8051288947, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  [:value [:main :free-sites] ("zadev1" "zadev3" "zadev4") ("zadev1" "zadev3")]
  :break
  [:value [:main :sites "zadev3"] nil {:bookings {531118335 {:id 531118335, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  [:value [:main :free-sites] ("zadev1" "zadev3") ("zadev1")]
  :break
  [:value [:main :sites "zadev4"] {:bookings {8051288947 {:id 8051288947, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {2437287445 {:id 2437287445, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 8051288947 {:id 8051288947, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  :break
  [:value [:main :sites "zadev4"] {:bookings {2437287445 {:id 2437287445, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 8051288947 {:id 8051288947, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {6513645767 {:id 6513645767, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 2437287445 {:id 2437287445, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 8051288947 {:id 8051288947, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  :break
  [:value [:main :sites "zadev3"] {:bookings {531118335 {:id 531118335, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {6524140876 {:id 6524140876, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 531118335 {:id 531118335, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  :break
  [:value [:main :sites "zadev3"] {:bookings {6524140876 {:id 6524140876, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 531118335 {:id 531118335, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {3080310733 {:id 3080310733, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 6524140876 {:id 6524140876, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 531118335 {:id 531118335, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  :break
  [:value [:main :sites "zadev3"] {:bookings {3080310733 {:id 3080310733, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 6524140876 {:id 6524140876, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 531118335 {:id 531118335, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {6934143109 {:id 6934143109, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 3080310733 {:id 3080310733, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 6524140876 {:id 6524140876, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 531118335 {:id 531118335, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  :break
  [:value [:main :sites "zadev1"] nil {:bookings {2542816186 {:id 2542816186, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  [:value [:main :free-sites] ("zadev1") ()]
  :break
  [:value [:main :sites "zadev2"] {:bookings {9324481540 {:id 9324481540, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {9324481540 {:id 9324481540, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 7349957774 {:id 7349957774, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  :break
  [:value [:main :sites "zadev4"] {:bookings {6513645767 {:id 6513645767, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 2437287445 {:id 2437287445, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 8051288947 {:id 8051288947, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {6513645767 {:id 6513645767, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 5692055597 {:id 5692055597, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 2437287445 {:id 2437287445, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 8051288947 {:id 8051288947, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  :break
  [:value [:main :free-sites] () ("zadev1")]
  [:value [:main :sites "zadev1"] {:bookings {2542816186 {:id 2542816186, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings []}]
 ]}