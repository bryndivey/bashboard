{:config {:order 0, :description "New", :name :bashboard}
 :data
 [
  [:node-create [] :map]
  [:node-create [:main] :map]
  [:node-create [:main :counter] :map]
  [:transform-enable [:main :counter] :inc [{:io.pedestal.app.messages/topic [:counter]}]]
  [:node-create [:main :sites] :map]
  [:transform-enable [:main :sites] :empty [{:io.pedestal.app.messages/topic [:sites "zadev1" :bookings]}]]
  [:node-create [:main :sites "zadev1"] :map]
  [:value [:main :sites "zadev1"] nil {:bookings {3863106914 {:id 3863106914, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 3025925685 {:id 3025925685, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
  [:node-create [:main :sites "zadev1" :bookings] :map]
  [:node-create [:main :sites "zadev1" :bookings 3863106914] :map]
  [:value [:main :sites "zadev1" :bookings 3863106914] nil {:id 3863106914, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]
  [:node-create [:main :sites "zadev1" :bookings 3025925685] :map]
  [:value [:main :sites "zadev1" :bookings 3025925685] nil {:id 3025925685, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]
  [:node-create [:main :sites-count] :map]
  [:value [:main :sites-count] nil 1]
  [:node-create [:main :free-sites] :map]
  [:value [:main :free-sites] nil ()]
  :break
  [:node-create [:main :sites "zadev1" :bookings 170517591] :map]
  [:value [:main :sites "zadev1" :bookings 170517591] nil {:id 170517591, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}]
  [:value [:main :sites "zadev1"] {:bookings {3863106914 {:id 3863106914, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 3025925685 {:id 3025925685, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}} {:bookings {3863106914 {:id 3863106914, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 3025925685 {:id 3025925685, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}, 170517591 {:id 170517591, :owner "bryn", :purpose "Testing shit", :start #inst "2013-01-05T00:00:00.000-00:00", :end #inst "2013-01-06T00:00:00.000-00:00"}}}]
 ]}